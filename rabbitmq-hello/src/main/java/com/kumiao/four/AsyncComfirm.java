package com.kumiao.four;

import com.kumiao.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author ：m
 * @date ：Created in 2022/4/17 11:16
 */
public class AsyncComfirm {
/*
     * 发布确认模式，
     * 1、单个确认
     * 2、批量确认
     * 3、异步批量确认
     * */
        // 批量发消息的个数
        public static final int MESSAGE_COUNT = 1000;

        public static void main(String[] args) throws Exception {
            //3、异步批量确认
            // 发布1000个异步确认消息，耗时36ms
            AsyncComfirm.publicMessageAsync();

        }



    public static void publicMessageAsync() throws Exception{
            Channel channel = RabbitMqUtils.getChannel();
            String queueName = UUID.randomUUID().toString();
            channel.queueDeclare(queueName,false,false,false,null);

            // 开启发布确认
            channel.confirmSelect();
            // 开始时间
            long begin = System.currentTimeMillis();


        /**
         * 线程有序的一个哈希表，选用于高迸发的情况下
         * 1、轻松地将序号与消息进行并联
         * 2、轻松批量删除，只要序号
         * 3、支持高并发
         */
        ConcurrentSkipListMap concurrentSkipListMap = new ConcurrentSkipListMap<>();


        // 消息确认成功回调函数
            ConfirmCallback ackCallback = (deliveryTag, multiply) -> {
                System.out.println("确认的消息："+deliveryTag);
                if (multiply) {
                    ConcurrentNavigableMap<Long,String> concurrentNavigableMap = concurrentSkipListMap.headMap(deliveryTag);
                    concurrentNavigableMap.clear();
                }else{
                    concurrentSkipListMap.remove(deliveryTag);
                }
            };

            // 消息确认失败回调函数
        /*    * 参数1：消息的标记
             * 参数2：是否为批量确认
             *
*/
            ConfirmCallback nackCallback = (deliveryTag,multiply) -> {
                System.out.println("未确认的消息："+deliveryTag);
            };

            // 准备消息的监听器，监听哪些消息成功，哪些消息失败

        /*     * 参数1：监听哪些消息成功
             * 参数2：监听哪些消息失败
             * */
            channel.addConfirmListener(ackCallback,nackCallback);

            // 批量发送消息
            for (int i = 0; i < MESSAGE_COUNT; i++) {
                String message = "消息" + i;
                channel.basicPublish("",queueName,null,message.getBytes(StandardCharsets.UTF_8));
                concurrentSkipListMap.put(channel.getNextPublishSeqNo(), message);
            }

            // 结束时间
            long end = System.currentTimeMillis();
            System.out.println("发布"+MESSAGE_COUNT+"个异步确认消息，耗时"+ (end - begin) + "ms");
        }



}
