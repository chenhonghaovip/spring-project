package com.honghao.cloud.userapi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Bap服务操作接口
 *
 * @author chenhonghao
 * @date 2019-05-14
 */
@FeignClient(
        name = OrderClient.SERVER_ID
//        ,fallbackFactory = BapFallbackFactory.class
)
public interface OrderClient {
    String SERVER_ID = "order-api";

    /**
     * 批量删除token
     *
     * @return 删除
     */
    @PostMapping("/bap/batch-del-token")
    String getInfo();

}
