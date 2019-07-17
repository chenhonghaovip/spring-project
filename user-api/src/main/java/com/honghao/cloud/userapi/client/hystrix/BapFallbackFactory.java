package com.honghao.cloud.userapi.client.hystrix;

import com.honghao.cloud.userapi.client.BapClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author chenhonghao
 * @date 2019-05-14
 */
@Slf4j
@Component
public class BapFallbackFactory implements FallbackFactory<BapClient> {
    @Override
    public BapClient create(Throwable throwable) {
        return () -> null;
    }
}
