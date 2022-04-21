package com.kumiaomiao.springboot.consumer;

import com.kumiaomiao.springboot.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ：m
 * @date ：Created in 2022/4/20 10:44
 */
@Component
@Slf4j
public class Consumer {

    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE_NAME)
    public void receiveConfirmMessage(Message message) {
        String msg = new String(message.getBody());
        System.out.println(new String(message.getBody()));
        log.info("接受到的队列confirm.queue消息：{}", msg);
    }
}
