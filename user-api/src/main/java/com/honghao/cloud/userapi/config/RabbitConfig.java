package com.honghao.cloud.userapi.config;


import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


/**
 * 消息队列配置
 *
 * @author chenhonghao
 * @date 2019-07-18 22:19
 */
@Slf4j
@EnableApolloConfig
@Configuration
public class RabbitConfig {
    /**
     * 用户信息推送队列
     */
    public static final String USER_PUSH_QUEUE = "user_push_queue";
    /**
     * Test测试队列
     */
    public static final String TEST_QUEUE = "test_queue";
    /**
     * 延迟10分钟
     */
    public static final String DELAY_TEN_MIN_DEATH = "delay_ten_min_death";

    /**
     * 10分钟复活消费队列
     */
    public static final String DELAY_TEN_MIN = "delay_ten_min";
    /** 短信发送队列 */
    public static final String QUEUE_MSG_SMS_SEND = "queue_msg_sms_send";
    /** 短信发送队列 延迟缓冲（按消息） */
    public static final String QUEUE_MSG_SMS_SEND_TTL = "queue_msg_sms_send_ttl";
    /** 短信发送队列交换机*/
    public static final String QUEUE_MSG_SMS_SEND_EXCHANGE = "queue_msg_sms_send_exchange";
    /** 短信发送队列 通过路由关键字 routing-key*/
    public static final String QUEUE_MSG_SMS_SEND_NAME = "queue_msg_sms_send_name";

    /**
     * 推送订单系统两分钟延迟队列
     */
    public static final String PUSH_TO_ORDER_QUEUE = "push_to_order_queue";
    /**
     * 推送订单系统重试队列
     */
    public static final String PUSH_TO_ORDER_QUEUE_RETRY = "push_to_order_queue_retry";
    /**
     * 推送订单系统失败队列
     */
    public static final String PUSH_TO_ORDER_QUEUE_FAIL = "push_to_order_queue_fail";

    /**
     * 消费队列
     */
    private static final String DELAY_PROCESS_QUEUE_NAME = "delay_process_queue_name";

    public static final String TEST = "test";
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
    /**
     * 简单消息监听容器
     * @param connectionFactory connectionFactory
     * @return SimpleMessageListenerContainer
     */
    @Bean
    public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        // 设置要监听的队列,可以实现对单个或多个queue的ack模式修改
        container.setQueueNames(TEST);
        container.setExposeListenerChannel(true);
        // 设置监听数据
        container.setMaxConcurrentConsumers(50);
        container.setConcurrentConsumers(1);
        // 设置确认模式手工确认
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
            String msg = new String(message.getBody(), StandardCharsets.UTF_8);

            try {
                System.out.println("receive msg : " + msg);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
                log.info("测试消息确认");
            }catch (Exception e){
                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
                log.info("测试消息异常");
            }
        });
        return container;
    }
    /**
     * 实际消费队列
     * @return
     */
    @Bean
    public Queue delayQueue() {
        return new Queue(RabbitConfig.DELAY_PROCESS_QUEUE_NAME,true,false,false);
    }

    /**
     * 短信发送队列
     * @return
     */
    @Bean
    public Queue smsQueue() {
        return new Queue(QUEUE_MSG_SMS_SEND, true);
    }

    /**
     * 短信发送队列延时
     * @return smsQueueDelayPerMessageTtl
     */
    @Bean
    public Queue smsQueueDelayPerMessageTtl() {
        Map<String, Object> arguments = new HashMap<>(16);
        arguments.put("x-dead-letter-exchange", QUEUE_MSG_SMS_SEND_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", QUEUE_MSG_SMS_SEND_NAME);
        return new Queue(QUEUE_MSG_SMS_SEND_TTL,true,false,false,arguments);
    }

    @Bean
    public DirectExchange smsDelayExchange(){
        // 默认是开启持久化的
        return new DirectExchange(QUEUE_MSG_SMS_SEND_EXCHANGE);
    }

    /**
     * 绑定队列到指定的交换机通过关键字topic
     * @return Binding
     */
    @Bean
    public Binding smsDelayBinding() {
        return BindingBuilder.bind(smsQueue())
                .to(smsDelayExchange())
                .with(QUEUE_MSG_SMS_SEND_NAME);
    }

    /**
     * 用户信息推送队列
     * @return queue
     */
    @Bean
    public Queue userPustQueue(){
        return new Queue(USER_PUSH_QUEUE);
    }

    /**
     * 延迟队列  consume
     * @return queue
     */
    @Bean
    public Queue delayTenQueue(){
        Map<String, Object> arguments = new HashMap<>(16);
        arguments.put("x-dead-letter-exchange", "");
        arguments.put("x-dead-letter-routing-key", DELAY_TEN_MIN);
        return new Queue(DELAY_TEN_MIN_DEATH, true, false, false, arguments);
    }

    /**
     * 延迟后转入消费队列
     * @return queue
     */
    @Bean
    public Queue delayConsumeQueue(){
        //队列默认是开启持久化的
        return new Queue(DELAY_TEN_MIN);
    }

    /**
     * 正常消费队列
     * @return queue
     */
    @Bean
    public Queue pushOrderQueue(){
        return new Queue(PUSH_TO_ORDER_QUEUE);
    }
    /**
     * 消费失败后转入延迟重试队列
     * @return queue
     */
    @Bean
    public Queue pushOrderQueueRetry(){
        Map<String, Object> map = new HashMap<>(8);
        //失败后重新将消息路由到具体exchange上
        map.put("x-dead-letter-exchange", "");
        //失败后重新将消息路由到具体routeKey上
        map.put("x-dead-letter-routing-key", PUSH_TO_ORDER_QUEUE);
        //消息延迟时间
        map.put("x-message-ttl", 5000);
        return new Queue(PUSH_TO_ORDER_QUEUE_RETRY, true, false, false, map);
    }
    /**
     * 三次失败后转入失败队列
     * @return queue
     */
    @Bean
    public Queue pushOrderQueueFail(){
        return new Queue(PUSH_TO_ORDER_QUEUE_FAIL);
    }

    @Bean
    public Queue testQueue(){
        return new Queue(TEST_QUEUE);
    }

    /**
     * 订单状态变更
     */
    public static final String WAYBILL_ORDER_EXCHANGE = "waybill_order_exchange";

    @Bean
    public Queue test01(){
        return new Queue("11111");
    }
    @Bean
    public Queue test02(){
        return new Queue("22222");
    }
    /**
     * 创建广播交换机
     * @return FanoutExchange
     */
    @Bean
    public FanoutExchange waybillOrderExchange(){
        return new FanoutExchange(WAYBILL_ORDER_EXCHANGE);
    }

    /**
     * 绑定队列1到指定的广播交换机
     * @return Binding
     */
    @Bean
    public Binding test01Binding() {
        return BindingBuilder.bind(test01())
                .to(waybillOrderExchange());
    }

    /**
     * 绑定队列2到指定的广播交换机
     * @return Binding
     */
    @Bean
    public Binding test02Binding() {
        return BindingBuilder.bind(test02())
                .to(waybillOrderExchange());
    }
}
