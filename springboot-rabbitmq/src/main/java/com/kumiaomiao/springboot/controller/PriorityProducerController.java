package com.kumiaomiao.springboot.controller;


import com.kumiaomiao.springboot.config.PriorityQueueConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/**
 * @author ：m
 * @date ：Created in 2022/4/21 16:35
 */
@RestController
@RequestMapping("/pri")
public class PriorityProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sedMsg")
    public String sendMsg() {

        String msg = "";
        for (int i = 0; i < 10; i++) {
            msg = "info" + i;
            //设置消息的优先级
            MessageProperties messageProperties = new MessageProperties();
            if (i == 5) {
                // 当i=5时，优先级为5，否则优先级为默认：0
                messageProperties.setPriority(5);
            }
            //生成发送消息
            Message message = new Message(msg.getBytes(StandardCharsets.UTF_8), messageProperties);
            //发送消息
            rabbitTemplate.sendAndReceive(
                    "",
                    PriorityQueueConfig.PRIORITY_QUEUE_NAME,
                    message);
        }
        return "ok";

    }
}
