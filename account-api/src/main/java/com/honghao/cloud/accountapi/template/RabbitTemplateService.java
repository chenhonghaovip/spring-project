package com.honghao.cloud.accountapi.template;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.accountapi.client.MessageClient;
import com.honghao.cloud.accountapi.dto.request.MsgInfoDTO;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.utils.SnowFlakeShortUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chenhonghao
 * @date 2020-07-25 16:14
 */
@Slf4j
@Component
public class RabbitTemplateService {
    private static SnowFlakeShortUrl snowFlake = new SnowFlakeShortUrl(2, 3);
    @Resource
    private MessageClient messageClient;

    public <T> BaseResponse sendMessage(T t,String queue,RabbitLoad rabbitLoad){
        MsgInfoDTO msgInfoDTO = MsgInfoDTO.builder().msgId(snowFlake.nextId()).content(JSON.toJSONString(t))
                .status(0).topic(queue).build();
        BaseResponse baseResponse = BaseResponse.error();
        try {
            baseResponse = messageClient.saveMessage(msgInfoDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
            return baseResponse;
        }

        if (baseResponse.isResult()){
            try {
                baseResponse = rabbitLoad.run();
            } catch (Exception e) {
                e.printStackTrace();
                return BaseResponse.error(e.getMessage());
            }

            if (baseResponse.isResult()){
                msgInfoDTO.setStatus(1);
                messageClient.send(msgInfoDTO);
            }
        }
        return baseResponse;
    }
}
