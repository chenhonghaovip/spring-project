package com.honghao.cloud.userapi.client.hystrix;

import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.client.OrderClient;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

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
                log.error("ssssssss:{}",throwable.getMessage());
                if (throwable instanceof HystrixTimeoutException){
                    System.out.println("超时处理");
                }
                return BaseResponse.successData("error");
            }

            @Override
            public BaseResponse<String> createUser1(String data) {
                return null;
            }

            @Override
            public List<WaybillBcList> batchQuery(List<String> list) {
                System.out.println(throwable.getMessage());
                return Collections.emptyList();
            }

            @Override
            public BaseResponse<WaybillBcList> singleQuery(String wId, String batchId) {
                return null;
            }

        };
    }
}
