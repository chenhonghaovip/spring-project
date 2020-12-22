package com.honghao.cloud.accountapi.client.hystrix;

import com.honghao.cloud.accountapi.client.OrderClient;
import com.honghao.cloud.basic.common.base.BaseResponse;
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
    public OrderClient create(Throwable throwable) {
        return new OrderClient() {
            @Override
            public BaseResponse create(String param) {
                log.info("account 服务降级");
                return BaseResponse.error();
            }

            @Override
            public BaseResponse queryWaybillBcList(String param) {
                return BaseResponse.error();
            }
        };
    }
}
