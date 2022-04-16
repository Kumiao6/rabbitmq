package com.kumiao.work;


import com.kumiao.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

/**
 * @author ：m
 * @date ：Created in 2022/4/16 14:18
 */
public class Work01 {
    /**
     * 队列名称
     */
    private final static String QUEUE_NAME = "hello";

    /**
     * 处理消息
     */
    public static void main(String[] args) {
        // 工具类
        RabbitMqUtils mqUtils = new RabbitMqUtils();
        // 获取通道
        Channel channel = mqUtils.getChannel();

        // 接收消息处理方法
        DeliverCallback deliverCallback = (consumerTag, message) -> {

            System.out.println("收到的消息：" + new String(message.getBody()));
        };
        // 接收失败处理方法
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息接收失败！" + consumerTag);
        };

        System.out.println("正在接收信息...");
        try {


            // 接收处理
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭：关闭后就不能再接收后面的信息
            //mqUtils.close();
        }
    }
}

