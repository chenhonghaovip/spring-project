package com.honghao.cloud.message.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * rabbit队列配置
 *
 * @author chenhonghao
 * @date 2020-07-30 16:10
 */
@Slf4j
@Configuration
public class RabbitConfig {

    public static final String TEST = "test";

    @Bean
    public Queue test() {
        return new Queue(TEST);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean("rabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer,
                                                                                     ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }
    /**
     * 简单消息监听容器
     * @param connectionFactory connectionFactory
     * @return SimpleMessageListenerContainer
     */
//    @Bean
//    public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        // 设置要监听的队列,可以实现对单个或多个queue的ack模式修改
//        container.setQueues(test());
//        container.setExposeListenerChannel(true);
//        // 设置监听数据
//        container.setMaxConcurrentConsumers(50);
//        container.setConcurrentConsumers(1);
//        // 设置确认模式手工确认
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
//            String msg = new String(message.getBody(), StandardCharsets.UTF_8);
//            System.out.println("receive msg : " + msg);
//            try {
//                if (TEST.equals(message.getMessageProperties().getConsumerQueue())){
//                    channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//                    log.info("测试消息确认");
//                }else {
//                    channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
//                    log.info("测试消息结果失败");
//                }
//            }catch (Exception e){
//                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
//                log.info("测试消息异常");
//            }
//        });
//        return container;
//    }
}
