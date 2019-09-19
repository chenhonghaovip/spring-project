package com.honghao.cloud.orderapi.listener.rabbitmq.producer;

import com.honghao.cloud.orderapi.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


/**
 * @author CHH
 */
@Slf4j
@Component
public class MessageSender {
	@Autowired
	@Qualifier("rabbitTemplate")
	private RabbitTemplate rabbitTemplate;

	/**
	 * 测试推送队列
	 * @param message 请求报文
	 */
	public void pushInfoUser(String message){
		rabbitTemplate.convertAndSend(RabbitConfig.TEST_QUEUE_1,message);
	}
}
