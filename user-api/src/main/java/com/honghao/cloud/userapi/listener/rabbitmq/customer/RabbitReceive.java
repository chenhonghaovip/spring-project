package com.honghao.cloud.userapi.listener.rabbitmq.customer;

import com.honghao.cloud.userapi.common.constant.QueueConstant;
import com.honghao.cloud.userapi.config.RabbitConfig;
import com.honghao.cloud.userapi.config.RabbitExchangeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * 消费者
 *
 * @author chenhonghao
 * @date 2019-07-31 15:28
 */
@Slf4j
@Configuration
public class RabbitReceive
{
    @Resource
    private RabbitConfig rabbitConfig;
    @Resource
    private RabbitExchangeConfig rabbitExchangeConfig;

    @Bean
    public SimpleMessageListenerContainer messageContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(rabbitConfig.connectionFactory());
        //设置要监听的队列,可以实现对单个或多个queue的ack模式修改
        container.setQueueNames(rabbitExchangeConfig.getArgs());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        //设置确认模式手工确认
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
            String msg = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println("receive msg : " + msg);
            if (QueueConstant.TEST_QUEUE.equals(message.getMessageProperties().getConsumerQueue())){
                try {
                    Boolean flag = true;
                    if (flag){
                        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
                        log.info("测试消息确认");
                    }else {
                        channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
                        log.info("测试消息结果失败");
                    }
                }catch (Exception e){
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
                    log.info("测试消息异常");
                }
            }

        });
        return container;
    }
}
