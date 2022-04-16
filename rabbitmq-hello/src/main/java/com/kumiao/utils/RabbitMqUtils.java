package com.kumiao.utils;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author ：m
 * @date ：Created in 2022/4/16 14:10
 */
public class RabbitMqUtils {
    private Connection connection = null;
    private Channel channel = null;




    public Channel getChannel() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        try {
            connection = connectionFactory.newConnection();
            System.out.println("获取连接！");
            channel = connection.createChannel();
            System.out.println("获取通道！");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return channel;
    }

    /**
     * 关闭通道及连接
     */
    public void close() {
        if (channel != null && channel.isOpen()) {
            try {
                channel.close();
                System.out.println("通道关闭！");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        if (connection != null && connection.isOpen()) {
            try {
                connection.close();
                System.out.println("连接关闭！");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
