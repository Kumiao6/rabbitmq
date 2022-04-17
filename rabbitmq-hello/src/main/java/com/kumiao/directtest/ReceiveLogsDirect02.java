package com.kumiao.directtest;

import com.kumiao.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author ：m
 * @date ：Created in 2022/4/17 16:56
 */
public class ReceiveLogsDirect02 {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        //声明一个队列
        channel.queueDeclare("console",false,false,false,null);

        //绑定交换机与队列
        channel.queueBind("console",EXCHANGE_NAME,"error");


        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("ReceiveLogsDirect01控制台打印接受到的消息：" + new String(message.getBody()));
        };

        channel.basicConsume("console",true,deliverCallback,consumerTag -> {});
    }
}
