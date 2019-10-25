package com.honghao.cloud.userapi.listener.rabbitmq.customer;

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

    @RabbitListener(queues = "11111")
    public void consumerBroadcastQueue1(String message){
        System.out.println("1111111111"+message);
    }

    @RabbitListener(queues = "22222")
    public void consumerBroadcastQueue2(String message){
        System.out.println("2222222222"+message);
    }
}
