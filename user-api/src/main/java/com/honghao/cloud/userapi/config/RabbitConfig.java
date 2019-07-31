package com.honghao.cloud.userapi.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;


/**
 * 消息队列配置
 *
 * @author chenhonghao
 * @date 2019-07-18 22:19
 */
@Slf4j
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

    /**
     * 默认的线程数
     */
    private static final int DEFAULT_CONCURRENT = 5;

    /**
     * 每个消费者获取最大投递数量 (默认50)
     */
    private static final int DEFAULT_PREFETCH_COUNT = 100;
    @Value("${spring.rabbitmq.addresses}")
    private String address;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean(name = "connectionFactory")
    @Primary
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(address);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    @Bean(name = "rabbitTemplate")
    @Primary
    public RabbitTemplate rabbitTemplate(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean(name = "factory")
    public SimpleRabbitListenerContainerFactory factory(SimpleRabbitListenerContainerFactoryConfigurer configurer,
                                                        @Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setPrefetchCount(DEFAULT_PREFETCH_COUNT);
        factory.setConcurrentConsumers(DEFAULT_CONCURRENT);
        configurer.configure(factory, connectionFactory);
        return factory;
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
     * @return
     */
    @Bean
    public Queue smsQueueDelayPerMessageTTL() {
        Map<String, Object> arguments = new HashMap<>(16);
        arguments.put("x-dead-letter-exchange", QUEUE_MSG_SMS_SEND_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", QUEUE_MSG_SMS_SEND_NAME);
        return new Queue(QUEUE_MSG_SMS_SEND_TTL,true,false,false,arguments);
    }

    @Bean
    public DirectExchange smsDelayExchange(){
        return new DirectExchange(QUEUE_MSG_SMS_SEND_EXCHANGE);
    }

    /**
     * 绑定队列到指定的
     * @return
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
}
