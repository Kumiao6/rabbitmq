package com.kumiaomiao.springboot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author ：m
 * @date ：Created in 2022/4/20 18:31
 */
@Component
@Slf4j
public class MyCallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 把当前类注入到 RabbitTemplate
     *
     * @PostConstruct 表示在执行当前类的构造时运行
     * 因为 ConfirmCallback 接口是 RabbitTemplate 的内部类
     */
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }




    /**
     *
     * 交换机确认回调方法
     * @param correlationData 回调消息
     * @param ack  交换机是否确认收到了消息： true：收到了； false:没有收到
     * @param cause 没有收到消息的原因
     */

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        //消息ID
        String id = correlationData != null ? correlationData.getId():"";

        if (ack) {
            //收到
            log.info("交换机收到了消息！ ID={}", id);
        }else {
            //未收到
            log.info("交换机没有收到消息! ID={};原因：{}", id, cause);
        }
    }


    /**
     * 消息回退回调方法
     * @param returnedMessage
     */
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.error(" 消息 {} ，被交换机 {} 退回，退回的原因： {}, 路由 Key ： {}",
                new String(returnedMessage.getMessage().getBody())
                , returnedMessage.getExchange()
                , returnedMessage.getReplyText()
                , returnedMessage.getRoutingKey());
    }
}
