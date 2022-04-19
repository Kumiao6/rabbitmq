package com.kumiaomiao.springboot.controller;

import com.kumiaomiao.springboot.config.DelayedQueueConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.Date;

/**
 * @author ：m
 * @date ：Created in 2022/4/18 17:09
 */

@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMsgController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     *
     * @param message
     * @return
     */
    @RequestMapping("/sendMsg/{message}")
    public String sendMsg(@PathVariable("message") String message) {
        rabbitTemplate.convertAndSend("X", "XA", message);
        rabbitTemplate.convertAndSend("X", "XB", message);
        log.info("当前时间：{}，发送给两个TTL队列：{}", LocalTime.now(), message);
        return "消息【" + message + "】已发送！";
    }

    /**
     * 发送有过期时间的消息
     *
     * @param message
     * @param ttlTime
     * @return
     */
    @RequestMapping("/sendExpirationMsg/{message}/{ttlTime}")
    public String sendMsg(
            @PathVariable("message") String message,
            @PathVariable("ttlTime") String ttlTime
    ) {
        // 消息处理器：设置过期时间
        MessagePostProcessor messagePostProcessor = msg -> {
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        };
        rabbitTemplate.convertAndSend("X", "XC", message, messagePostProcessor);

        log.info(" 当前时间： {} ，发送给 QC 队列： {} ，过期时间： {}ms", LocalTime.now(), message, ttlTime);
        return " 消息【 " + message + " 】已发送！ ";
    }

    /**
     * 发送有过期时间的消息（基于插件）
     *
     * @param message
     * @param delayTime
     * @return
     */
    @RequestMapping("/sendDelayMsg/{message}/{delayTime}")
    public String sendMsg(
            @PathVariable("message") String message,
            @PathVariable("delayTime") Integer delayTime
    ) {
        System.out.println(message + delayTime);
        // 消息处理器：设置延迟时间
        MessagePostProcessor messagePostProcessor = msg ->{
            msg.getMessageProperties().setDelay(delayTime);
            return msg;
        };

        rabbitTemplate.convertAndSend(
                DelayedQueueConfig.DELAYED_EXCHANGE_NAME,
                DelayedQueueConfig.DELAYED_ROUTING_KEY,
                message,
                messagePostProcessor
        );

        log.info("当前时间：{}，发送给延迟队列：{}，延迟时间：{}ms", LocalTime.now(),message,delayTime);
        return "消息【" + message + "】已发送！";
    }



}

