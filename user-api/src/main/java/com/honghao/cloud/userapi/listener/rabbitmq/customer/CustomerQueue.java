package com.honghao.cloud.userapi.listener.rabbitmq.customer;

import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.userapi.client.MessageClient;
import com.honghao.cloud.userapi.config.RabbitConfig;
import com.honghao.cloud.userapi.dto.request.MsgInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author CHH
 */
@Slf4j
@Component
public class CustomerQueue {
    @Resource
    private MessageClient messageClient;
	/**
	 * 消费队列信息
	 * @param msgInfoDTO json字符串
	 */
	@RabbitListener(queues = RabbitConfig.TEST)
	public void process(MsgInfoDTO msgInfoDTO) {
		try {
//            MsgInfoDTO msgInfoDTO = JSON.parseObject(str, MsgInfoDTO.class);
            log.info("队列消息为：{}",msgInfoDTO);
            BaseResponse complete = messageClient.complete(msgInfoDTO);
            if (!complete.isResult()){
                log.error(complete.getRemark());
            }
            //手动ack通知队列成功
//			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			log.info("ACK_QUEUE_A 接受信息成功");
		}catch (Exception e){
			//丢弃消息
//			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
			//消息重新返回队列
//			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
			log.info("消息队列发送操作异常:{}",e.getMessage());
		}
	}
}
