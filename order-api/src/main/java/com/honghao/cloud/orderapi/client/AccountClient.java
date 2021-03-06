package com.honghao.cloud.orderapi.client;

import com.honghao.cloud.orderapi.client.hystrix.AccountFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.honghao.cloud.orderapi.client.AccountClient.SERVICE_ID;

/**
 * 账户服务接口
 *
 * @author chenhonghao
 * @date 2019-07-31 13:19
 */
@FeignClient(name = SERVICE_ID, fallbackFactory = AccountFallbackFactory.class)
public interface AccountClient {
    String SERVICE_ID = "ACCOUNT-API";

    /**
     * 账户服务调用
     *
     * @param param string
     */
    @PostMapping("/create")
    void create(@RequestBody String param);
}
