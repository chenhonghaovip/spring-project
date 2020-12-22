package com.honghao.cloud.message.component;

import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.basic.common.factory.ThreadPoolFactory;
import com.honghao.cloud.message.client.AccountClient;
import com.honghao.cloud.message.client.OrderClient;
import com.honghao.cloud.message.domain.entity.MsgInfo;
import com.honghao.cloud.message.domain.mapper.MsgInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author CHH
 */
@Slf4j
@Component
public class MessageSender {
    private static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = ThreadPoolFactory.buildScheduledThreadPoolExecutor(1, "timer");
    public RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) -> {
        MsgInfo msgInfo = new MsgInfo();
        String id = Objects.requireNonNull(correlationData).getId();
        assert id != null;
        msgInfo.setMsgId(Long.valueOf(id));
        if (!ack) {
            msgInfo.setRetryTime(msgInfo.getRetryTime() != null ? msgInfo.getRetryTime() + 1 : 0);
            log.info("ackMQSender 消息发送失败" + cause + Objects.requireNonNull(correlationData).toString());
        } else {
            log.info("ackMQSender 消息发送成功 ");
            msgInfo.setStatus(2);
        }
    };
    @Resource
    private OrderClient orderClient;
    @Resource
    private AccountClient accountClient;
    @Resource
    private MsgInfoMapper msgInfoMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void init() {
        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {

            List<MsgInfo> msgInfos = msgInfoMapper.selectByStatus();
            msgInfos.forEach(each -> {
                try {
                    if (Integer.valueOf(0).equals(each.getStatus())) {
                        // 当状态为0时，说明需要主动询问业务发起方业务操作是否成功来判断消息的下一步处理，
                        // 如果业务处理失败，删除消息，如果成功，则修改状态为待发送并且发送消息到rabbit中间件
                        BaseResponse baseResponse = accountClient.businessStatus(each.getMsgId());
                        if (baseResponse.isResult()) {
                            each.setStatus(1);
                            msgInfoMapper.updateByPrimaryKeySelective(each);
                            publicQueueProcessing(each, each.getTopic());
                        } else {
                            msgInfoMapper.deleteByPrimaryKey(each.getMsgId());
                        }
                    } else if (Integer.valueOf(1).equals(each.getStatus())) {
                        // 当状态为1时，说明业务发起方已经成功处理，但是消息服务投递消息到中间件时出现异常，所以需要重新投递
                        publicQueueProcessing(each, each.getTopic());
                    } else {
                        // 可以通过手动ack机制实现
                        // 当状态为2时，说明消息已经投递到消息中间件队列中，等待消费方完成消费后发起回调
                        // 如果长时间消息状态为已发送，未变为已完成，需要向消费方主动发起确认
                        // BaseResponse baseResponse = orderClient.queryStatus(each.getMsgId());
                        BaseResponse baseResponse = orderClient.queryStatus(each.getMsgId());
                        if (baseResponse.isResult()) {
                            each.setStatus(1);
                            msgInfoMapper.updateByPrimaryKeySelective(each);
                        } else {
                            publicQueueProcessing(each, each.getTopic());
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            });
        }, 0, 20, TimeUnit.MINUTES);
    }

    /**
     * 通用信息推送队列
     *
     * @param queue   队列信息
     * @param msgInfo 请求报文
     */
    public void commonPush(String queue, MsgInfo msgInfo) {
        /**
         * exchange到queue成功,则不回调return
         * exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
         */
        rabbitTemplate.setReturnCallback((message1, i, s, s1, s2) -> {
            log.info("ackMQSender 发送消息被退回" + s1 + s2);
            msgInfo.setRetryTime(msgInfo.getRetryTime() != null ? msgInfo.getRetryTime() + 1 : 0);
        });
        /**
         * 如果设置了spring.rabbitmq.publisher-confirms=true(默认是false),生产者会收到rabitmq-server返回的ack
         * 这个回调方法里面没有原始消息,相当于只是一个通知作用
         *
         * 如果消息没有到exchange,则confirm回调,ack=false
         * 如果消息到达exchange,则confirm回调,ack=true
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                msgInfo.setRetryTime(msgInfo.getRetryTime() != null ? msgInfo.getRetryTime() + 1 : 0);
                log.info("ackMQSender 消息发送失败" + cause + Objects.requireNonNull(correlationData).toString());
            } else {
                log.info("ackMQSender 消息发送成功 ");
                msgInfo.setStatus(2);
            }
            msgInfoMapper.updateByPrimaryKeySelective(msgInfo);
        });
        CorrelationData correlationData = new CorrelationData(String.valueOf(msgInfo.getMsgId()));
        rabbitTemplate.convertAndSend(queue, msgInfo, correlationData);
    }

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
