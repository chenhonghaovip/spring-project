package com.honghao.cloud.userapi.listener.rabbitmq.customer;

import com.honghao.cloud.userapi.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

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
	public void process(String str) {
		try {
			log.info("银行卡结算功能开始,RequestBody:{}",str);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
		
}
