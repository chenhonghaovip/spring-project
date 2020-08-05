package com.honghao.cloud.accountapi.client;

import com.honghao.cloud.accountapi.client.hystrix.OrderFallbackFactory;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 账户服务接口
 *
 * @author chenhonghao
 * @date 2019-07-31 13:19
 */
@FeignClient(name = OrderClient.SERVICE_ID,fallbackFactory = OrderFallbackFactory.class)
public interface OrderClient {
    String SERVICE_ID="order-api";

    /**
     * 账户服务调用
     * @param param string
     * @return BaseResponse
     */
    @PostMapping("/create")
    BaseResponse create(@RequestBody String param);

    /**
     * 账户服务调用
     * @param param string
     * @return BaseResponse
     */
    @PostMapping("/create1")
    BaseResponse queryWaybillBcList(@RequestBody String param);

}
