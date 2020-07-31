package com.honghao.cloud.userapi.listener.rabbitmq.producer;

import com.honghao.cloud.userapi.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
	 * 公用延迟队列处理
	 * @param queueName 队列名称
	 * @param content 队列内容
	 * @param time 延迟时间 毫秒数
	 */
	public <T> void  publicDelayQueueProcessing(T content, String queueName , final int time) {
		int delayTime = time*60*1000;
		MessagePostProcessor messagePostProcessor = message -> {
			message.getMessageProperties().setExpiration(String.valueOf(delayTime));
            //开启消息持久化
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
			return message;
		};
		rabbitTemplate.convertAndSend(queueName, content, messagePostProcessor);
	}

	/**
	 * 公用队列处理
	 * @param str 队列信息
	 * @param queueName 队列名称
	 */
	public void publicQueueProcessing(String str , String queueName){
		rabbitTemplate.convertAndSend(queueName ,str);
	}
}
