package com.kumiao.one;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author ：m
 * @date ：Created in 2022/4/16 13:06
 */
public class Consumer {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //声明 接受信息
        DeliverCallback deliverCallback = (consumerTag,message) -> {
            System.out.println(new String(message.getBody()));
        };

        //声明 取消消息
        CancelCallback cancelCallback = consumer -> {
            System.out.println("消息消费被中断");
        };
        /**
         *         * 消费者接收消息
         *         * 参数1：表示消费哪个UI列
         *         * 参数2：消费成功之后，是否需要自动应答，true表示自动应答，false表示手动应答
         *         * 参数3：消费者成功消费的回调
         *         * 参数4：消费者取消消费的回调
         */

        channel.basicConsume(QUEUE_NAME, true,deliverCallback,cancelCallback);
    }

}
