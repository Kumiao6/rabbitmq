package com.kumiao.five;

import com.kumiao.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.apache.commons.io.FileUtils;
import org.springframework.amqp.rabbit.connection.RabbitUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author ：m
 * @date ：Created in 2022/4/17 15:44
 */
/*
 * 消息接收
 * */
/*
 * 消息接收
 * */
public class ReceiveLogs02 {

    //交换机名称
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();

        //声明一个队列,名称随机，当消费者断开与队列的连接时，队列自动删除
        String queueName = channel.queueDeclare().getQueue();

        //绑定交换机与队列
        channel.queueBind(queueName,EXCHANGE_NAME,"fanout");
        System.out.println("等待接受消息，把接受到的消息打印在屏幕上...");


        DeliverCallback deliverCallback = (consumerTag,message) -> {
            System.out.println("ReceiveLogs02控制台打印接受到的消息：" + new String(message.getBody()));
        };

        channel.basicConsume(queueName,true,deliverCallback,consumerTag -> {});
    }
}

