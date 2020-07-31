package com.honghao.cloud.orderapi.listener.rabbitmq.customer;

import com.honghao.cloud.orderapi.dto.request.CardDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 测试队列
 *
 * @author chenhonghao
 * @date 2019-07-23 22:08
 */
@Component
@Slf4j
public class TesQueue {

//    @RabbitListener(queues = RabbitConfig.TEST_QUEUE_1)
    public void sendMessage(String message){
        try {
            log.info("");
            log.info("开始消费,{}",message);
            CardDTO cardDTO=new CardDTO();
        }catch (Exception e){
            log.info("消息队列发送操作异常");
        }
    }
}
