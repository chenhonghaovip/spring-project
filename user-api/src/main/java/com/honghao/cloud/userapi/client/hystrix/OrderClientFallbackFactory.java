package com.honghao.cloud.userapi.client.hystrix;

import com.alibaba.fastjson.JSONObject;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.userapi.client.OrderClient;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
            public BaseResponse<String> createUser(JSONObject data) {
                if (throwable instanceof IOException){

                }
                return BaseResponse.successData("");
            }

            @Override
            public BaseResponse<String> createUser1(String data) {
                return null;
            }

            @Override
            public BaseResponse<List<WaybillBcList>> batchQuery(List<String> list) {
                System.out.println(throwable.getMessage());
                return BaseResponse.successData(list.stream().map(each-> WaybillBcList.builder().wId(each).build()).collect(Collectors.toList()));
            }

            @Override
            public BaseResponse<WaybillBcList> singleQuery(String wId, String batchId) {
                return null;
            }

        };
    }
}
