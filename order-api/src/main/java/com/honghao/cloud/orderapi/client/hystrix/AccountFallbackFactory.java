package com.honghao.cloud.orderapi.client.hystrix;

import com.honghao.cloud.orderapi.client.AccountClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * 熔断降级配置
 *
 * @author chenhonghao
 * @date 2019-07-31 13:24
 */
@Slf4j
public class AccountFallbackFactory implements FallbackFactory<AccountClient> {
    @Override
    public AccountClient create(Throwable throwable) {
        return new AccountClient() {
            @Override
            public void create(String param) {
                log.info("account 服务降级");
            }
        };
    }
}
