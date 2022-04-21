package com.kumiaomiao.springboot.consumer;

import com.kumiaomiao.springboot.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ：m
 * @date ：Created in 2022/4/20 18:48
 */
@Component
@Slf4j
public class ConfirmQueueConsumer {
    /**
     * 监听确认队列
     */

    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE_NAME)
    public void receiveConfirmMsg(Message message) {
        String msg = new String(message.getBody());
        log.info("收到的消息：【{}】",msg);
    }
}
