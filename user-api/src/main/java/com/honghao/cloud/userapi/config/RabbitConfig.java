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
     * 延迟10分钟
     */
    public static final String DELAY_TEN_MIN_DEATH = "delay_ten_min_death";

    /**
     * 10分钟复活消费队列
     */
    public static final String DELAY_TEN_MIN = "delay_ten_min";
    /** 短信发送队列 */
    public static final String QUEUE_MSG_SMS_SEND = "msg:sms:send";

    /** 短信发送队列 DLX */
    public static final String DLX_MSG_SMS_SEND = "msg:sms:send:dlx";
    /** 短信发送队列 DLX */
    public static final String DLX_MSG_SMS_SEND1 = "msg:sms:send:dlx1";

    /** 短信发送队列 延迟缓冲（按消息） */
    public static final String QUEUE_DELAY_PER_MESSAGE_TTL_MSG_SMS_SEND = "delay:per:message:msg:sms:send";
    /** 短信发送队列 */
    public static final String QUEUE_MSG_SMS_SEND_NAME = "msg:sms:send:name";
    /** 短信发送队列 */
    public static final String QUEUE_MSG_SMS_SEND_NAME1 = "msg:sms:send:name1";
    /**
     * 支付系统的mq交换机
     */
    public static final String PAY_DIRECT_EXCHANGE = "pay_direct_exchange";
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
     * 延迟队列 TTL 名称
     * todo
     */
    private static final String REGISTER_DELAY_QUEUE = "dev.book.register.delay.queue";
    private static final String DELAY_PROCESS_QUEUE_NAME = "delay_process_queue_name";
    /**
     * DLX，dead letter发送到的 exchange
     */
    public static final String DELAY_EXCHANGE_NAME = "delay_exchange_name";

    /**
     * TTL，延迟发送到的 exchange
     */
    public static final String QUEUE_TTL_EXCHANGE_NAME = "queue_ttl_exchange_name";
    /**
     * routing key 名称
     * TODO 此处的 routingKey 很重要要,具体消息发送在该 routingKey 的
     */
    public static final String DELAY_ROUTING_KEY = "";
    public static final String DELAY_ROUTING_UNIQUE_KEY = "unique";
    public static final String ROUTING_KEY = "all";


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
     * 延迟队列配置,超时时间不一致
     * @return Queue
     */
    @Bean
    public Queue delayProcessQueue() {
        Map<String, Object> params = new HashMap<>(8);
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-dead-letter-exchange", QUEUE_TTL_EXCHANGE_NAME);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        params.put("x-dead-letter-routing-key", ROUTING_KEY);
        return new Queue(REGISTER_DELAY_QUEUE, true, false, false, params);
    }
    /**
     * 延迟队列配置,超时时间一致
     * @return Queue
     */
    @Bean
    public Queue delayProcessQueueUnique() {
        Map<String, Object> params = new HashMap<>(8);
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-dead-letter-exchange", QUEUE_TTL_EXCHANGE_NAME);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        params.put("x-dead-letter-routing-key", ROUTING_KEY);
        params.put("x-message-ttl", 5000);
        return new Queue(REGISTER_DELAY_QUEUE, true, false, false, params);
    }

    /**
     * 死信交换机配置
     * @return DirectExchange
     */
    @Bean
    public DirectExchange delayExchange() {
        return new DirectExchange(RabbitConfig.DELAY_EXCHANGE_NAME);
    }
    /**
     * 延迟交换机
     * @return
     */
    @Bean
    public DirectExchange  perQueueTTLExchange(){
        DirectExchange directExchange = new DirectExchange(QUEUE_TTL_EXCHANGE_NAME,true,false);
        return directExchange;
    }

    /**
     * 绑定死信队列
     * @return Binding
     */
    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(ROUTING_KEY);
    }

    /**
     * 绑定延迟队列之延时相同
     * @return Binding
     */
    @Bean
    public Binding queueTTLBinding() {
        return BindingBuilder.bind(delayProcessQueue()).to(perQueueTTLExchange()).with(RabbitConfig.DELAY_ROUTING_KEY);
    }

    /**
     * 绑定延迟队列之延时不同
     * @return Binding
     */
    @Bean
    public Binding queueTTLBinding2() {
        return BindingBuilder.bind(delayProcessQueueUnique()).to(perQueueTTLExchange()).with(RabbitConfig.DELAY_ROUTING_UNIQUE_KEY);
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
     * 短信发送队列
     * @return
     */
    @Bean
    public Queue smsQueueDelayPerMessageTTL() {
        Map<String, Object> arguments = new HashMap<>(16);
        arguments.put("x-dead-letter-exchange", DLX_MSG_SMS_SEND);
        arguments.put("x-dead-letter-routing-key", QUEUE_MSG_SMS_SEND_NAME);
        return new Queue(QUEUE_DELAY_PER_MESSAGE_TTL_MSG_SMS_SEND,true,false,false,arguments);
    }

    @Bean
    public DirectExchange smsDelayExchange(){
        return new DirectExchange(DLX_MSG_SMS_SEND);
    }

    @Bean
    public DirectExchange smsDelayExchange1(){
        return new DirectExchange(DLX_MSG_SMS_SEND1);
    }

    @Bean
    public Binding smsDelayBinding() {
        return BindingBuilder.bind(smsQueue())
                .to(smsDelayExchange())
                .with(QUEUE_MSG_SMS_SEND_NAME);
    }

    @Bean
    public Binding smsDelayBinding1() {
        return BindingBuilder.bind(smsQueueDelayPerMessageTTL())
                .to(smsDelayExchange1())
                .with(QUEUE_MSG_SMS_SEND_NAME1);
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
        return new Queue(PUSH_TO_ORDER_QUEUE_RETRY);
    }
    /**
     * 三次失败后转入失败队列
     * @return queue
     */
    @Bean
    public Queue pushOrderQueueFail(){
        return new Queue(PUSH_TO_ORDER_QUEUE_FAIL);
    }
}
