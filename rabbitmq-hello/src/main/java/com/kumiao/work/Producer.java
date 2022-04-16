package com.kumiao.work;

import com.kumiao.utils.RabbitMqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author ：m
 * @date ：Created in 2022/4/16 14:41
 */
public class Producer {
    /**
     * 队列名称
     */
    private final static String QUEUE_NAME = "hello";

    /**
     * 发送消息
     *
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        // 工具类
        RabbitMqUtils mqUtils = new RabbitMqUtils();
        // 获取通道
        Channel channel = mqUtils.getChannel();

        // 声明队列
        try {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        } catch (IOException e) {
            e.printStackTrace();

        }
        // 消息
        String message = "";
        try {
            // 循环发送消息
            for (int i = 1; i < 21; i++) {
                message = "Hello World! " + i;
                channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                System.out.println("第" + i + "条消息已发送！");
     /*           try {
                    // 休眠1秒
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
            System.out.println("发送结束！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭
//            mqUtils.close();


        }
    }
}
