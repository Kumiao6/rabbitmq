package com.kumiao.eight;

import com.kumiao.utils.RabbitMqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * @author ：m
 * @date ：Created in 2022/4/17 23:52
 */
public class Producer {
    //普通交换机的名称
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();

        //设置过期时间
/*
        AMQP.BasicProperties properties = new AMQP.BasicProperties()
                .builder()
                .expiration("1000")
                .build();
*/




        for (int i = 1; i < 111; i++) {
            String message = "info" + i;
            channel.basicPublish(NORMAL_EXCHANGE,"zhangsan",null,message.getBytes());
            System.out.println("producer生产的信息是：info" + i);
        }
    }
}
