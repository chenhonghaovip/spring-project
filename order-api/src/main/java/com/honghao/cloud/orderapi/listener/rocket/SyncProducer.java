package com.honghao.cloud.orderapi.listener.rocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 同步生产者
 *
 * @author chenhonghao
 * @date 2021-02-05 16:10
 */
@Slf4j
@Component
public class SyncProducer {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void test(){
        RocketMQLocalTransactionListener transactionListener = new RocketMQLocalTransactionListener() {

            @Override
            public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
                return null;
            }

            @Override
            public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
                return null;
            }
        };
        rocketMQTemplate.createAndStartTransactionMQProducer("",transactionListener,null,null);
    }

}
