package com.kumiao.eight;

import com.kumiao.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：m
 * @date ：Created in 2022/4/17 22:32
 */
public class Consumer01 {

    //普通交换机名称
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    //死信交换机名称
    public static final String DEAD_EXCHANGE = "dead_exchange";

    //普通队列名称
    public static final String NORMAL_QUEUE = "normal_queue";

    //死信队列名称
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws  Exception{
        Channel channel = RabbitMqUtils.getChannel();

        //声明死信和普通的交换机类型为direct
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);

        //声明普通队列
        HashMap<String, Object> arguments = new HashMap<>();

        //过期时间
//        arguments.put("x-message-ttl",1000);
        //正常队列设置死信队列
        arguments.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        //设置死信RoutingKey
        arguments.put("x-dead-letter-routing-key","lisi");
        //设置死信队列的长度限制
//        arguments.put("x-max-length", 6);

        //声明死信和普通队列
        channel.queueDeclare(NORMAL_QUEUE,false,false,false,arguments);
        channel.queueDeclare(DEAD_QUEUE,false,false,false,null);

        //绑定普通的交换机与普通的队列
        channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE,"zhangsan");
        //绑定死信的交换机与死信的队列
        channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"lisi");
        System.out.println("等待接收消息......");

        DeliverCallback deliverCallback = (consumerTag,message) -> {
            String msg = new String(message.getBody(), "UTF-8");
            if (msg.equals("info5")) {
                System.out.println("Consumer01接收的消息是：" + msg + ":此信息是被拒绝的");
                channel.basicReject(message.getEnvelope().getDeliveryTag(),false);
            } else {
                System.out.println("Consumer01接收的消息是：" + msg);
                channel.basicAck(message.getEnvelope().getDeliveryTag(),false);

            }

        };
        //开启手动应答
        channel.basicConsume(NORMAL_QUEUE,false,deliverCallback,consumerTag->{});
    }
}
