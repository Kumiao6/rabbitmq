package com.kumiao.four;

import com.kumiao.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author ：m
 * @date ：Created in 2022/4/16 21:19
 */
/*
 * 发布确认模式，
 * 1、单个确认
 * 2、批量确认
 * 3、异步批量确认
 * */
public class ComfirmMessage {

    // 批量发消息的个数
    public static final int MESSAGE_COUNT = 1000;

    public static void main(String[] args) throws Exception {
        // 1、单个确认
        // 发布1000个单独确认消息，耗时567ms
//        ComfirmMessage.publishMessageIndividually();
        //2、批量确认
        ComfirmMessage.publishMessageIndividually();

    }

    public static void publishMessageIndividually() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        String queueName = UUID.randomUUID().toString();
        /*
         * 生成一个队列
         * 参数1：队列名称
         * 参数2：队列里面的消息是否持久化，默认情况下，消息存储在内存中
         * 参数3：该队列是否只供一个消费者进行消费，是否进行消费共享，true可以多个消费者消费，
         *        false只能一个消费者消费
         * 参数4：是否自动删除：最后一个消费者断开连接之后，该队列是否自动删除，true则自动删除，
         *        false不自动删除
         * 参数5：其他参数
         * */
        channel.queueDeclare(queueName,false,false,false,null);

        // 开启发布确认
        channel.confirmSelect();
        // 开始时间
        long begin = System.currentTimeMillis();

        // 批量发消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            /*
             * 发送一个消息
             * 参数1：发送到哪个交换机
             * 参数2：路由的key值是那个，本次是队列的名称
             * 参数3：其他参数信息
             * 参数4：发送消息的消息体
             * */
            channel.basicPublish("",queueName,null,message.getBytes(StandardCharsets.UTF_8));
            // 单个消息马上进行发布确认
            boolean flag = channel.waitForConfirms();
            if (flag){
                System.out.println("消息发送成功");
            }
        }

        // 结束时间
        long end = System.currentTimeMillis();
        System.out.println("发布"+MESSAGE_COUNT+"个单独确认消息，耗时"+ (end - begin) + "ms");
    }
}




