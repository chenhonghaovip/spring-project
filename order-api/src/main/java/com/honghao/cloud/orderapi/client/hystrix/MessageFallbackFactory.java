package com.honghao.cloud.orderapi.client.hystrix;

import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.orderapi.client.MessageClient;
import com.honghao.cloud.orderapi.dto.common.BatchMsgInfoDTO;
import com.honghao.cloud.orderapi.dto.common.MsgInfoDTO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 熔断降级配置
 *
 * @author chenhonghao
 * @date 2019-07-31 13:24
 */
@Slf4j
@Component
public class MessageFallbackFactory implements FallbackFactory<MessageClient> {

    @Override
    public MessageClient create(Throwable cause) {
        return new MessageClient() {

            @Override
            public BaseResponse saveMessage(MsgInfoDTO msgInfoDTO) {
                log.error(cause.getMessage());
                return BaseResponse.error();
            }

            @Override
            public BaseResponse send(MsgInfoDTO msgInfoDTO) {
                return BaseResponse.error();
            }

            @Override
            public BaseResponse delete(MsgInfoDTO msgInfoDTO) {
                return BaseResponse.error();
            }

            @Override
            public BaseResponse<List<Long>> batchSaveMessage(BatchMsgInfoDTO batchMsgInfoDTO) {
                return BaseResponse.error();
            }

            @Override
            public BaseResponse batchSend(BatchMsgInfoDTO batchMsgInfoDTO) {
                return BaseResponse.error();
            }
        };
    }
}
