package com.honghao.cloud.userapi.config;


import lombok.extern.slf4j.Slf4j;
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
     * 10分钟复活
     */
    public static final String DELAY_TEN_MIN = "delay_ten_min";

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
}
