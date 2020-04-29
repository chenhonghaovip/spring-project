package com.honghao.cloud.userapi.listener.rabbitmq.producer;

import com.honghao.cloud.userapi.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;


/**
 * @author CHH
 */
@Slf4j
@Component
public class MessageSender {
	@Resource
	@Qualifier("rabbitTemplate")
	private RabbitTemplate rabbitTemplate;

	/**
	 * 用户信息推送队列
	 * @param message 请求报文
	 */
	public void pushInfoUser(String message){
		/**
		 * exchange到queue成功,则不回调return
		 * exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
		 */
		rabbitTemplate.setReturnCallback((message1, i, s, s1, s2) -> {
			log.info("ackMQSender 发送消息被退回" + s1 + s2);
		});
		/**
		 * 如果设置了spring.rabbitmq.publisher-confirms=true(默认是false),生产者会收到rabitmq-server返回的ack
		 * 这个回调方法里面没有原始消息,相当于只是一个通知作用
		 *
		 * 如果消息没有到exchange,则confirm回调,ack=false
		 * 如果消息到达exchange,则confirm回调,ack=true
		 */
		this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
			if (!ack) {
				log.info("ackMQSender 消息发送失败" + cause + Objects.requireNonNull(correlationData).toString());
				// 消息重新发送到消息队列
				pushInfoUser(message);
			} else {
				log.info("ackMQSender 消息发送成功 ");
			}
		});
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

	public void sendMessage(Object content) {
		log.info("开始短信消息发送");
		MessagePostProcessor messagePostProcessor = message -> {
			message.getMessageProperties().setExpiration(String.valueOf(2*1000));
			//开启消息持久化
			message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
			return message;
		};
		rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_MSG_SMS_SEND_TTL, content, messagePostProcessor);
	}

	/**
	 * 用户信息推送队列
	 * @param message 请求报文
	 */
	public void testQueue(String message){
		rabbitTemplate.setMandatory(true);
		rabbitTemplate.setReturnCallback((message1, i, s, s1, s2) -> {
			log.info("ackMQSender 发送消息被退回" + s1 + s2);
		});
		/**
		 * 如果设置了spring.rabbitmq.publisher-confirms=true(默认是false),生产者会收到rabitmq-server返回的ack
		 * 这个回调方法里面没有原始消息,相当于只是一个通知作用
		 */
		this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
			if (!ack) {
				log.info("ackMQSender 消息发送失败" + cause + correlationData.toString());
			} else {
				log.info("ackMQSender 消息发送成功 ");
			}
		});
		rabbitTemplate.convertAndSend(RabbitConfig.TEST_QUEUE,message);
	}

	/**
	 * 用户信息推送队列
	 * @param message 请求报文
	 */
	public void outQueue(String message){
		System.out.println("用户信息推送队列");
		rabbitTemplate.convertAndSend(RabbitConfig.WAYBILL_ORDER_EXCHANGE,"",message);
	}

	/**
	 * 数据迁移测试
	 * @param message 请求报文
	 */
	public void test01(String message){

		rabbitTemplate.convertAndSend(RabbitConfig.TEST_1,message);
	}


	public void productConfirm(String message){

	}
}
