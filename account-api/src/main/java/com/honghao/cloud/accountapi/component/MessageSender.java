package com.honghao.cloud.accountapi.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author CHH
 */
@Slf4j
@Component
public class MessageSender {
    @Resource
    private RabbitTemplate rabbitTemplate;


    /**
     * 公用延迟队列处理
     *
     * @param queueName 队列名称
     * @param content   消息内容
     * @param time      延迟时间 毫秒数
     */
    public void publicDelayQueueProcessing(Object content, String queueName, final int time) {
        int delayTime = time * 60 * 1000;
        MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setExpiration(String.valueOf(delayTime));
            //开启消息持久化
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        };
        rabbitTemplate.convertAndSend(queueName, content, messagePostProcessor);
    }

    /**
     * 公用队列处理
     *
     * @param t         消息内容
     * @param queueName 队列名称
     */
    public void publicQueueProcessing(Object t, String queueName) {
        rabbitTemplate.convertAndSend(queueName, t);
    }
}
