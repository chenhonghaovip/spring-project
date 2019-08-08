package com.honghao.cloud.orderapi.config;


import lombok.extern.slf4j.Slf4j;
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
}
