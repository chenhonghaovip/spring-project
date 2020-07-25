package com.honghao.cloud.accountapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chenhonghao
 * @date 2020-07-25 20:01
 */
@Data
@Component
@ConfigurationProperties(prefix = "account")
public class ApolloConfig {
    /**
     * 预计插入量
     */
    private long expectedInsertions;

    /**
     * 错误率
     */
    private double fpp;
}
