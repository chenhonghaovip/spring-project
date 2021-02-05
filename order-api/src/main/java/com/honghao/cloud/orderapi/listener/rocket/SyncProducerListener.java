package com.honghao.cloud.orderapi.listener.rocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 监听服务
 *
 * @author chenhonghao
 * @date 2021-02-05 16:04
 */
@Slf4j
@Component
public class SyncProducerListener implements TransactionListener {
    private ConcurrentHashMap<String, Object> localTrans = new ConcurrentHashMap<>();
    /**
     - 发送prepare消息成功此方法被回调，该方法用于执行本地事务
     - @param msg 回传的消息，利用transactionId即可获取到该消息的唯一Id
     - @param arg 调用send方法时传递的参数，当send时候若有额外的参数可以传递到send方法中，这里能获取到
     - @return 返回事务状态，COMMIT：提交 ROLLBACK：回滚 UNKNOW：回调
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        return null;
    }

    /**
     - @param msg 通过获取transactionId来判断这条消息的本地事务执行状态
     - @return 返回事务状态，COMMIT：提交 ROLLBACK：回滚 UNKNOW：回调
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        return null;
    }





}
