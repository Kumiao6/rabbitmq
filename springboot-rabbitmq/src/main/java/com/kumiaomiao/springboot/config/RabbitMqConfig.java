package com.kumiaomiao.springboot.config;

import org.apache.tomcat.jni.Directory;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置交换机、队列及绑定关系
 */
@Configuration
public class RabbitMqConfig {
    /**
     * 交换机：普通、死信
     */
    public static final String EXCHANGE_X = "X";
    public static final String DEAD_LETTER_EXCHANGE_Y = "Y";
    /**
     * 队列：普通、死信
     */
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    public static final String QUEUE_C = "QC";
    public static final String DEAD_LETTER_QUEUE_D = "QD";

    /**
     * 获取普通交换机
     * @return
     */
    @Bean("exchangeX")
    public DirectExchange getExchangeX(){
        return new DirectExchange(EXCHANGE_X);
    }

    /**
     * 获取死信交换机
     * @return
     */
    @Bean("deadLetterExchangeY")
    public DirectExchange getDeadLetterExchangeY(){
        return new DirectExchange(DEAD_LETTER_EXCHANGE_Y);
    }

    /**
     * 获取普通队列A
     * @return
     */
    @Bean("queueA")
    public Queue getQueueA(){
        Map<String, Object> arguments = new HashMap<>(3);
        // 设置死信交换机
        arguments.put("x-dead-letter-exchange",DEAD_LETTER_EXCHANGE_Y);
        // 设置死信交换机绑定的RoutingKey
        arguments.put("x-dead-letter-routing-key","YD");
        // 设置过期时间TTL：单位毫秒
        arguments.put("x-message-ttl",10000);

        return QueueBuilder.durable(QUEUE_A).withArguments(arguments).build();
    }

    /**
     * 获取普通队列B
     * @return
     */
    @Bean("queueB")
    public Queue getQueueB(){
        Map<String, Object> arguments = new HashMap<>(3);
        // 设置死信交换机
        arguments.put("x-dead-letter-exchange",DEAD_LETTER_EXCHANGE_Y);
        // 设置死信交换机绑定的RoutingKey
        arguments.put("x-dead-letter-routing-key","YD");
        // 设置过期时间TTL：单位毫秒
        arguments.put("x-message-ttl",40000);

        return QueueBuilder.durable(QUEUE_B).withArguments(arguments).build();
    }

    /**
     * 获取普通通用队列C
     * 不设置过期时间
     * @return
     */
    @Bean("queueC")
    public Queue getQueueC(){
        Map<String, Object> arguments = new HashMap<>(3);
        // 设置死信交换机
        arguments.put("x-dead-letter-exchange",DEAD_LETTER_EXCHANGE_Y);
        // 设置死信交换机绑定的RoutingKey
        arguments.put("x-dead-letter-routing-key","YD");
        // 设置过期时间TTL：单位毫秒
        //arguments.put("x-message-ttl",40000);

        return QueueBuilder.durable(QUEUE_C).withArguments(arguments).build();
    }

    /**
     * 获取死信队列D
     * @return
     */
    @Bean("queueD")
    public Queue getQueueD(){
        return QueueBuilder.durable(DEAD_LETTER_QUEUE_D).build();
    }

    /**
     * 绑定
     * 队列QA 绑定 交换机X
     * @return
     */
    @Bean
    public Binding queueABindExchangeX(
            @Qualifier("queueA") Queue queue,
            @Qualifier("exchangeX") DirectExchange exchange
    ){
        return BindingBuilder.bind(queue).to(exchange).with("XA");
    }



    /**
     * 绑定
     * 队列QC 绑定 交换机X
     * @return
     */
    @Bean
    public Binding queueCBindExchangeX(
            @Qualifier("queueC") Queue queue,
            @Qualifier("exchangeX") DirectExchange exchange
    ){
        return BindingBuilder.bind(queue).to(exchange).with("XC");
    }

    /**
     * 绑定
     * 队列QB 绑定 交换机X
     * @return
     */
    @Bean
    public Binding queueBBindExchangeX(
            @Qualifier("queueB") Queue queue,
            @Qualifier("exchangeX") DirectExchange exchange
    ){
        return BindingBuilder.bind(queue).to(exchange).with("XB");
    }

    /**
     * 绑定
     * 队列QD 绑定 交换机Y
     * @return
     */
    @Bean
    public Binding queueDBindExchangeY(
            @Qualifier("queueD") Queue queue,
            @Qualifier("deadLetterExchangeY") DirectExchange exchange
    ){
        return BindingBuilder.bind(queue).to(exchange).with("YD");
    }

}

