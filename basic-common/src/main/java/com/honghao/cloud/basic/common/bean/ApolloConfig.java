package com.honghao.cloud.basic.common.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chenhonghao
 * @date 2020-07-26 10:52
 */
@ConfigurationProperties(prefix = "redis.bloom")
public class ApolloConfig {
    /**
     * 预计插入量
     */
    private long expectedInsertions = 1000;

    /**
     * 错误率
     */
    private double fpp = 0.01;

    public long getExpectedInsertions() {
        return expectedInsertions;
    }

    public void setExpectedInsertions(long expectedInsertions) {
        this.expectedInsertions = expectedInsertions;
    }

    public double getFpp() {
        return fpp;
    }

    public void setFpp(double fpp) {
        this.fpp = fpp;
    }
}
