package com.honghao.cloud.orderapi.listener.rabbitmq.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 *
 * @author chenhonghao
 * @date 2019-10-24 11:17
 */
@Slf4j
@Component
public class FanoutExchangeReceiver {

    @RabbitListener(queues = "33333")
    public void consumerBroadcastQueue1(String message){
        System.out.println("3333333"+message);
    }
}
