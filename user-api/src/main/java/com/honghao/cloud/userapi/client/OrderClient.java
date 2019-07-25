package com.honghao.cloud.userapi.client;

import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.client.hystrix.OrderClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Bap服务操作接口
 *
 * @author chenhonghao
 * @date 2019-05-14
 */
@FeignClient(
        name = OrderClient.SERVER_ID
        ,fallbackFactory = OrderClientFallbackFactory.class
)
public interface OrderClient {
    String SERVER_ID = "ORDER-API";

    /**
     * 批量删除token
     * @param data string
     * @return 删除
     */
    @PostMapping("/order/create")
    @ResponseBody
    BaseResponse<String> createUser(@RequestParam String data);

    /**
     * 批量删除token
     * @param data string
     * @return 删除
     */
    @PostMapping("/order/create1")
    @ResponseBody
    BaseResponse<String> createUser1(@RequestParam String data);
}
