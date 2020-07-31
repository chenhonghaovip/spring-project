package com.honghao.cloud.userapi.client.hystrix;

import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.userapi.client.MessageClient;
import com.honghao.cloud.userapi.dto.request.MsgInfoDTO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
                return BaseResponse.error();
            }

            @Override
            public BaseResponse updateStatus(MsgInfoDTO msgInfoDTO) {
                return BaseResponse.error();
            }

            @Override
            public BaseResponse delete(MsgInfoDTO msgInfoDTO) {
                return null;
            }
        };
    }
}
