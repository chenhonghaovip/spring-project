package com.honghao.cloud.message.client.hystrix;

import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.message.client.OrderClient;
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
public class OrderFallbackFactory implements FallbackFactory<OrderClient> {

    @Override
    public OrderClient create(Throwable cause) {
        return new OrderClient() {
            @Override
            public BaseResponse queryStatus(long msgId) {
                return BaseResponse.error();
            }
        };
    }
}
