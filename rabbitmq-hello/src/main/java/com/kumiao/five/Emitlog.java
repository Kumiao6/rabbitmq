package com.kumiao.five;

import com.kumiao.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.amqp.rabbit.connection.RabbitUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author ：m
 * @date ：Created in 2022/4/17 16:10
 */
/*
 *  发消息 交换机
 * */
public class Emitlog {
    // 交换机的名称
    public  static  final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws  Exception{
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发出的消息："+ message);
        }
    }
}

