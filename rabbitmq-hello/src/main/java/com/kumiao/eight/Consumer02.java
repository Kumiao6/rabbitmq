package com.kumiao.eight;

import com.kumiao.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.util.HashMap;

/**
 * @author ：m
 * @date ：Created in 2022/4/17 22:32
 */
public class Consumer02 {

    //死信队列名称
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws  Exception{

        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("等待接收信息");


        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("收到的信息是：" + new String(message.getBody()));

        };

        channel.basicConsume(DEAD_QUEUE, true, deliverCallback, consumerTag->{});

    };


    }
