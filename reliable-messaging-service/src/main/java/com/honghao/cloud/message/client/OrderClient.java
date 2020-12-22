package com.honghao.cloud.message.client;

import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.message.client.hystrix.OrderFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 账户服务接口
 *
 * @author chenhonghao
 * @date 2019-07-31 13:19
 */
@FeignClient(name = OrderClient.SERVICE_ID, fallbackFactory = OrderFallbackFactory.class)
public interface OrderClient {
    String SERVICE_ID = "order-api";

    /**
     * 账户服务调用
     *
     * @param msgId msgId
     * @return BaseResponse
     */
    @GetMapping("/queryStatus")
    BaseResponse queryStatus(@RequestParam("msgId") long msgId);
}
