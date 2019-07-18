package com.honghao.cloud.userapi.listener.rabbitmq.customer;

import com.honghao.cloud.userapi.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author CHH
 */
@Component
@RabbitListener(queues = RabbitConfig.USER_PUSH_QUEUE, containerFactory = "factory")
public class TenMinDelay {
	private Logger logger= LoggerFactory.getLogger(TenMinDelay.class);

	/**
	 * 消费队列信息
	 * @param str json字符串
	 */
	@RabbitHandler
	public void process(String str) {
		try {
			logger.info("银行卡结算功能开始,RequestBody:{}",str);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
		
}
