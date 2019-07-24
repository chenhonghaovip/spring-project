package com.honghao.cloud.userapi.client;

import com.honghao.cloud.userapi.base.BaseResponse;
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
//        name = "192.168.0.110:8082"
//        ,fallbackFactory = BapFallbackFactory.class
)
public interface OrderClient {
    String SERVER_ID = "order-api";

    /**
     * 批量删除token
     * @param data string
     * @return 删除
     */
    @PostMapping("/order/create")
    @ResponseBody
    BaseResponse<String> createUser(@RequestParam String data);

}
