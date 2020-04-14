package com.honghao.cloud.orderapi.listener.rabbitmq.customer;

import com.honghao.cloud.orderapi.config.RabbitConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author CHH
 */
@Slf4j
@Component
@RabbitListener(queues = RabbitConfig.USER_PUSH_QUEUE, containerFactory = "factory")
public class TenMinDelay {
	/**
	 * 消费队列信息
	 * @param str json字符串
	 */
	@RabbitHandler
	public void process(String str, Channel channel, Message message) throws IOException {
		try {
			log.info("发送短信,{}",str);
			//手动ack通知队列成功
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			log.info("ACK_QUEUE_A 接受信息成功");
		}catch (Exception e){
			//丢弃消息
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
			//消息重新返回队列
//			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
			log.info("消息队列发送操作异常");
		}
	}
		
}
