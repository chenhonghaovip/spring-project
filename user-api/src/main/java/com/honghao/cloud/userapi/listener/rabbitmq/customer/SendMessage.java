package com.honghao.cloud.userapi.listener.rabbitmq.customer;

import com.alibaba.fastjson.JSONObject;
import com.honghao.cloud.userapi.config.RabbitConfig;
import com.honghao.cloud.userapi.listener.rabbitmq.producer.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 发送短信
 *
 * @author chenhonghao
 * @date 2019-07-23 22:08
 */
@Component
@Slf4j
public class SendMessage {
    @Resource
    private MessageSender messageSender;

    @RabbitListener(queues = RabbitConfig.QUEUE_MSG_SMS_SEND)
    public void sendMessage(JSONObject jsonObject){
        try {
            log.info("发送短信");
            throw new RuntimeException("yichang");
        }catch (Exception e){
            messageSender.sendMessage(jsonObject);
        }

    }
}
