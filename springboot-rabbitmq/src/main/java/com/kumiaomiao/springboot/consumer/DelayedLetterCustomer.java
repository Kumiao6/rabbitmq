package com.kumiaomiao.springboot.consumer;

import com.kumiaomiao.springboot.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * @author ：m
 * @date ：Created in 2022/4/19 17:54
 */
@Slf4j
@Component
public class DelayedLetterCustomer {

    /**
     * 监听延迟队列消息
     * @param message
     */
    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE_NAME)
    public void receiveDelayedQueue(Message message){
        String msg = new String(message.getBody());
        log.info(" 时间：{}，接收到的消息：{}", LocalTime.now(),msg);
    }
}
