package com.kumiaomiao.springboot.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：m
 * @date ：Created in 2022/4/21 16:28
 */
@Configuration
public class PriorityQueueConfig {
    /**
     *  优先级队列
     */
    public static final String PRIORITY_QUEUE_NAME = "priority.queue";

    /**
     * 获取优先级队列
     */
    @Bean("priorityQueue")
    public Queue getPriorityQueue(){
        // 简写
        //return QueueBuilder.durable(PRIORITY_QUEUE_NAME).maxPriority(10).build();

        // 通用写法
        Map<String, Object> arguments = new HashMap<>(1);
        arguments.put("x-max-priority", 10);
        return QueueBuilder.durable(PRIORITY_QUEUE_NAME).withArguments(arguments).build();
    }
}
