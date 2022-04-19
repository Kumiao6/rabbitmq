package com.kumiaomiao.springboot.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.channels.Channel;
import java.time.LocalTime;
import java.util.Date;


/*
 * 队列TTL 消费者
 * */
@Slf4j
@Component
public class DeadLetterQueueConsumer {
    /**
     * 监听队列QD
     * @param message
     */
    @RabbitListener(queues = "QD")
    //public void receiveD(Message message, Channel channel) throws Exception {
    public void receiveQueueD(Message message){
        String msg = new String(message.getBody());
        log.info("时间：{}，接收到的消息：{}", LocalTime.now(),msg);
    }
}
