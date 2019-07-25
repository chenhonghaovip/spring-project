package com.honghao.cloud.userapi.client.hystrix;

import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.client.OrderClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 订单服务调用接口服务降级功能
 *
 * @author chenhonghao
 * @date 2019-07-25 10:10
 */
@Slf4j
@Component
public class OrderClientFallbackFactory implements FallbackFactory<OrderClient> {

    @Override
    public OrderClient create(Throwable throwable) {
        return new OrderClient() {
            @Override
            public BaseResponse<String> createUser(String data) {
                return null;
            }

            @Override
            public BaseResponse<String> createUser1(String data) {
                return null;
            }
        };
    }
}
