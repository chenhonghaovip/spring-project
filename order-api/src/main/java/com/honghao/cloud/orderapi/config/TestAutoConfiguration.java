package com.honghao.cloud.orderapi.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chenhonghao
 * @date 2020-03-13 14:38
 */
@Component
@EnableConfigurationProperties(ParamConfig.class)
public class TestAutoConfiguration {
    @Resource
    private ParamConfig paramConfig;

}
