package com.kumiaomiao.springboot.consumer;


import com.kumiaomiao.springboot.config.PriorityQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ：m
 * @date ：Created in 2022/4/21 16:42
 */
@Slf4j
@Component
public class PriorityQueueConsumer {
    /**
     * 监听优先级队列
     * @param message
     */
    @RabbitListener(queues = PriorityQueueConfig.PRIORITY_QUEUE_NAME)
    public void receiveConfirmMsg(Message message){
        String msg = new String(message.getBody());
        log.info("接收到的消息：【{}】", msg);
    }
}