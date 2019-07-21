package com.honghao.cloud.userapi.listener.rabbitmq.producer;

import com.honghao.cloud.userapi.config.RabbitConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


/**
 * @author CHH
 */
@Component
public class MessageSender {
	@Autowired
	@Qualifier("rabbitTemplate")
	private AmqpTemplate rabbitTemplate;

	/**
	 * 用户信息推送队列
	 * @param message 请求报文
	 */
	public void pushInfoUser(String message){
		rabbitTemplate.convertAndSend(RabbitConfig.USER_PUSH_QUEUE,message);
	}
	/**
	 * 延迟处理 time 分钟数 action 处理的类名，方法为 doAction data 处理参数
	 * @param content 请求报文
	 */
	public void delayDoAction(Object content) {
		MessagePostProcessor messagePostProcessor = message -> {
			message.getMessageProperties().setExpiration(String.valueOf(60*1000));
			return message;
		};
		rabbitTemplate.convertAndSend(RabbitConfig.DELAY_TEN_MIN_DEATH, content, messagePostProcessor);
	}
}