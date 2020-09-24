package com.honghao.cloud.orderapi.template;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.utils.SnowFlakeShortUrl;
import com.honghao.cloud.orderapi.client.MessageClient;
import com.honghao.cloud.orderapi.dto.common.MsgInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chenhonghao
 * @date 2020-07-25 16:14
 */
@Slf4j
@Component
public class RabbitTemplateService implements EnvironmentAware {
    private static SnowFlakeShortUrl snowFlake = new SnowFlakeShortUrl(2, 3);
    private Environment environment;
    @Resource
    private MessageClient messageClient;

    public <T> BaseResponse sendMessage(T t,String queue,RabbitLoad rabbitLoad){
        MsgInfoDTO msgInfoDTO = MsgInfoDTO.builder().businessId(String.valueOf(snowFlake.nextId())).content(JSON.toJSONString(t))
                .status(0).topic(queue).appId(environment.getProperty("spring.application.name")).url("/order/batchQuery").build();

        BaseResponse baseResponse = BaseResponse.error();
        try {
            baseResponse = messageClient.saveMessage(msgInfoDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
            return baseResponse;
        }

        Object data;
        if (baseResponse.isResult()){
            try {
                data = baseResponse.getData();
                baseResponse = rabbitLoad.run();
            } catch (Exception e) {
                e.printStackTrace();
                return BaseResponse.error(e.getMessage());
            }

            if (baseResponse.isResult()){
                msgInfoDTO.setMsgId((Long) data);
                messageClient.send(msgInfoDTO);
            }
        }
        return baseResponse;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
