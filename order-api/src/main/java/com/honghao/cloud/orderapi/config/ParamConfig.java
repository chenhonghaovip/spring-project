package com.honghao.cloud.orderapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 参数类配置
 *
 * @author chenhonghao
 * @date 2019-07-20 01:08
 */
@Data
@Component
@ConfigurationProperties(prefix = "honghao")
public class ParamConfig {
    /**
     * 名字
     */
    private String name;
}
