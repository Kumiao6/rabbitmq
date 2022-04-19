package com.kumiaomiao.springboot.config;


import com.rabbitmq.client.BuiltinExchangeType;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.QueueBuilder;


import java.util.HashMap;
import java.util.Map;

/**
 * @author ：m
 * @date ：Created in 2022/4/19 16:01
 */
@Configuration
public class DelayedQueueConfig {
    /**
     * 交换机、队列、 RoutingKey 
     */
    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";
    public static final String DELAYED_QUEUE_NAME = "delayed.queue";
    public static final String DELAYED_ROUTING_KEY = "delayed.routingkey"; 

    /**
     * 自定义交换机 
     *
     * @return
     */
    @Bean("delayedExchange")
    public CustomExchange getDelayedExchange() {
        // 参数 
        Map<String, Object> arguments = new HashMap<>(1);
        // 交换机类型：直接 
        arguments.put("x-delayed-type", BuiltinExchangeType.DIRECT.getType());
        //arguments.put("x-delayed-type","direct"); 

        // 自定义交换机 
        return new CustomExchange(
                DELAYED_EXCHANGE_NAME,
                "x-delayed-message",
                true,
                false,
                arguments);
    } 

    /**
     * 延迟队列 
     * 和普通队列一样 
     *
     * @return
     */
    @Bean("delayedQueue")
    public Queue getDelayedQueue() {
        //return new Queue(DELAYED_QUEUE_NAME); 
        return QueueBuilder.durable(DELAYED_QUEUE_NAME).build();
    } 

    /**
     * 绑定 
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding delayedQueueBindDelayedExchange(
            @Qualifier("delayedQueue") Queue queue,
            @Qualifier("delayedExchange") CustomExchange exchange
    ) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(DELAYED_ROUTING_KEY)
                .noargs();
    }
} 

