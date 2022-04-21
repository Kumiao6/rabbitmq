package com.kumiaomiao.springboot.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：m
 * @date ：Created in 2022/4/20 10:14
 */
@Configuration
public class ConfirmConfig {
    /**
     * 交换机、队列及RoutingKey 
     */
    public static final String CONFIRM_EXCHANGE_NAME = "confirm.exchange";
    public static final String CONFIRM_QUEUE_NAME = "confirm.queue";
    public static final String CONFIRM_ROUTING_KEY = "key1"; 

    /**
     * 获取确认交换机 
     * @return
     */
    @Bean("confirmExchange")
    public DirectExchange getConfirmExchange(){
        return new DirectExchange(CONFIRM_EXCHANGE_NAME);
    } 

    /**
     * 获取确认队列 
     * @return
     */
    @Bean("confirmQueue")
    public Queue getConfirmQueue(){
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    } 

    /**
     * 绑定 
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding confirmQueueBindConfirmExchange(
            @Qualifier("confirmQueue") Queue queue,
            @Qualifier("confirmExchange") DirectExchange exchange
    ){
        return BindingBuilder.bind(queue).to(exchange).with(CONFIRM_ROUTING_KEY);
    }
} 
