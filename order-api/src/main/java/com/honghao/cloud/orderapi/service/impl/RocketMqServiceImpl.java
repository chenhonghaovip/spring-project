package com.honghao.cloud.orderapi.service.impl;

import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.orderapi.service.RocketMqService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * rocketmq测试
 *
 * @author chenhonghao
 * @date 2021-02-05 15:58
 */
@Slf4j
@Service
public class RocketMqServiceImpl implements RocketMqService {

    @Resource
    private TransactionMQProducer transactionMQProducer;

    @Resource
    private DefaultMQProducer defaultMQProducer;

    @Override
    public BaseResponse sendMessage() {
        Message message = new Message();
        try {
            transactionMQProducer.sendMessageInTransaction(message,null);
            transactionMQProducer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {

                }

                @Override
                public void onException(Throwable throwable) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
