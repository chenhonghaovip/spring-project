package com.honghao.cloud.userapi.listener.rabbitmq.customer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.honghao.cloud.userapi.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 延迟队列
 *
 * @author chenhonghao
 * @date 2019-07-20 11:01
 */
@Component
@Slf4j
@RabbitListener(queues = RabbitConfig.DELAY_TEN_MIN)
public class DelayQueueCustomer {
    /**
     * 延迟消费队列信息
     * @param jsonObject json字符串
     */
    @RabbitHandler
    public void process(JSONObject jsonObject) {
        try {
            log.info("延迟队列开始执行,RequestBody:{}", JSON.toJSONString(jsonObject));
            log.info(">>>>>>>>>>>>>{}",jsonObject.getString("value"));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
