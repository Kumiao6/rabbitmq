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
    public static final String BACKUP_EXCHANGE_NAME = "backup.exchange";

    public static final String CONFIRM_QUEUE_NAME = "confirm.queue";
    public static final String BACKUP_QUEUE_NAME = "backup.queue";
    public static final String WARNING_QUEUE_NAME = "warning.queue";

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
     * 备份交换机
     * 类型： fanout
     * @return
     */
    @Bean("backupExchange")
    public FanoutExchange getBackupExchange(){
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    /*
    获取备份队列
     */
    @Bean("backupQueue")
    public Queue getBackupQueeu() {
        return QueueBuilder.durable(BACKUP_EXCHANGE_NAME).build();

    }


    /**
     * 获取报警队列
     * @return
     */
    @Bean("warningQueue")
    public Queue getWarningQueue(){
        return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
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
     * 备份队列 绑定 备份交换机
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding backupQueueBindBackupExchange(
            @Qualifier("backupQueue") Queue queue,
            @Qualifier("backupExchange") FanoutExchange exchange
    ){
        return BindingBuilder.bind(queue).to(exchange);
    }


    /**
     * 报警队列 绑定 备份交换机
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding warningQueueBindBackupExchange(
            @Qualifier("warningQueue") Queue queue,
            @Qualifier("backupExchange") FanoutExchange exchange
    ){
        return BindingBuilder.bind(queue).to(exchange);
    }

} 
