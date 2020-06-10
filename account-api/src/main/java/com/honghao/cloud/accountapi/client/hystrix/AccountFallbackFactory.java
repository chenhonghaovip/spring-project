package com.honghao.cloud.accountapi.client.hystrix;

import com.honghao.cloud.accountapi.client.AccountClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 熔断降级配置
 *
 * @author chenhonghao
 * @date 2019-07-31 13:24
 */
@Slf4j
@Component
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
