package com.honghao.cloud.orderapi.template;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.basic.common.dto.ProtocolConstants;
import com.honghao.cloud.basic.common.netty.NettyUtils;
import com.honghao.cloud.orderapi.client.MessageClient;
import com.honghao.cloud.orderapi.dto.common.BatchMsgInfoDTO;
import com.honghao.cloud.orderapi.dto.common.MsgInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author chenhonghao
 * @date 2020-07-25 16:14
 */
@Slf4j
@Component
public class RabbitTemplateService {
    @Resource
    private MessageClient messageClient;

    public BaseResponse sendMessage(MsgInfoDTO msgInfoDTO, RabbitLoad rabbitLoad) {

        BaseResponse baseResponse = BaseResponse.error();
        try {
            baseResponse = messageClient.saveMessage(msgInfoDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
            return baseResponse;
        }

        Object data;
        if (baseResponse.isResult()) {
            try {
                data = baseResponse.getData();
                baseResponse = rabbitLoad.run();
            } catch (Exception e) {
                e.printStackTrace();
                return BaseResponse.error(e.getMessage());
            }

            if (baseResponse.isResult()) {
                msgInfoDTO.setMsgId((Long) data);
                messageClient.send(msgInfoDTO);
            }
        }
        return baseResponse;
    }

    public BaseResponse sendMessage(MsgInfoDTO msgInfoDTO, RabbitLoad rabbitLoad, int type) {

        BaseResponse baseResponse = BaseResponse.error();
        try {
            baseResponse = NettyUtils.sendMessageLoad(msgInfoDTO, ProtocolConstants.INSERT);
        } catch (Exception e) {
            log.error(e.getMessage());
            return baseResponse;
        }

        Object data;
        if (baseResponse.isResult()) {
            try {
                data = baseResponse.getData();
                baseResponse = rabbitLoad.run();
            } catch (Exception e) {
                e.printStackTrace();
                return BaseResponse.error(e.getMessage());
            }

            if (baseResponse.isResult()) {
                msgInfoDTO.setMsgId((Long) data);
                try {
                    NettyUtils.sendMessageLoad(msgInfoDTO, ProtocolConstants.UPDATE);
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
        }
        return baseResponse;
    }


    public BaseResponse batchMessage(BatchMsgInfoDTO batchMsgInfoDTO, RabbitLoad rabbitLoad) {
        BaseResponse baseResponse = BaseResponse.error();
        try {
            baseResponse = messageClient.batchSaveMessage(batchMsgInfoDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
            return baseResponse;
        }

        List<Long> list;
        if (baseResponse.isResult()) {
            try {
                Object data = baseResponse.getData();
                list = JSON.parseArray(JSON.toJSONString(data), Long.class);
                baseResponse = rabbitLoad.run();
            } catch (Exception e) {
                e.printStackTrace();
                return BaseResponse.error(e.getMessage());
            }

            if (baseResponse.isResult()) {
                batchMsgInfoDTO.setMsgIds(list);
                messageClient.batchSend(batchMsgInfoDTO);
            }
        }
        return baseResponse;
    }

    public BaseResponse batchMessage(BatchMsgInfoDTO batchMsgInfoDTO, RabbitLoad rabbitLoad, int type) {
        BaseResponse baseResponse = BaseResponse.error();
        try {
            baseResponse = NettyUtils.sendMessageLoad(batchMsgInfoDTO, ProtocolConstants.BATCH_INSERT);
        } catch (Exception e) {
            log.error(e.getMessage());
            return baseResponse;
        }

        List<Long> list;
        if (baseResponse.isResult()) {
            try {
                Object data = baseResponse.getData();
                list = JSON.parseArray(JSON.toJSONString(data), Long.class);
                baseResponse = rabbitLoad.run();
            } catch (Exception e) {
                e.printStackTrace();
                return BaseResponse.error(e.getMessage());
            }

            if (baseResponse.isResult()) {
                batchMsgInfoDTO.setMsgIds(list);
                try {
                    NettyUtils.sendMessageLoad(batchMsgInfoDTO, ProtocolConstants.BATCH_UPDATE);
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
        }
        return baseResponse;
    }
}
