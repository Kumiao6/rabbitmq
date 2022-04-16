package com.kumiao.three;

import com.kumiao.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author ：m
 * @date ：Created in 2022/4/16 20:41
 */
/*
 * 消息在手动应答时是不丢失、放回队列中重新消费
 * */
public class Task2 {

    // 队列名称
    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();

        // 声明队列
        channel.queueDeclare(TASK_QUEUE_NAME,false,false,false,null);


        // 消息
        String message = "";
        for (int i = 1; i < 21; i++) {
            message = "Hello World! " + i;
            channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println("第" + i + "条消息已发送！");
        }
        System.out.println("发送结束！");


    }
}