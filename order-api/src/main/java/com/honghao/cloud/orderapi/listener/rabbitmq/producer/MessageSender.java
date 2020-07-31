package com.honghao.cloud.orderapi.listener.rabbitmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author CHH
 */
@Slf4j
@Component
public class MessageSender {
	@Resource
	private RabbitTemplate rabbitTemplate;

}
