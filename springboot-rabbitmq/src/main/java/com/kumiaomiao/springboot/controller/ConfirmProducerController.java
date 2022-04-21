package com.kumiaomiao.springboot.controller;

import com.kumiaomiao.springboot.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Correlation;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：m
 * @date ：Created in 2022/4/20 18:04
 */
@Slf4j
@RestController
@RequestMapping("//")
public class ConfirmProducerController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/senMsg/{msg}")
    public String sendMsg(@PathVariable("msg") String msg) {
        //CorrelationData供回退方法调用
        CorrelationData correlationData = new CorrelationData("1");
        rabbitTemplate.convertAndSend(
                ConfirmConfig.CONFIRM_EXCHANGE_NAME,
                ConfirmConfig.CONFIRM_ROUTING_KEY,
                msg,
                correlationData
        );

        log.info("消息：【" + msg + "】己发送!");
        return "OK";

    }

    @GetMapping("/senMsg1/{msg}")
    public String sendMsg2(@PathVariable("msg") String msg) {
        //CorrelationData供回退方法调用
        CorrelationData correlationData = new CorrelationData("1");
        rabbitTemplate.convertAndSend(
                ConfirmConfig.CONFIRM_EXCHANGE_NAME,
                ConfirmConfig.CONFIRM_ROUTING_KEY + "123",
                msg,
                correlationData
        );

        log.info("消息：【" + msg + "】己发送!");
        return "OK";

    }
}
