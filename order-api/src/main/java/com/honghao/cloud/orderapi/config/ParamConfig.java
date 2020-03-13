package com.honghao.cloud.orderapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

/**
 * 参数类配置
 *
 * @author chenhonghao
 * @date 2019-07-20 01:08
 */
@Data
@ConfigurationProperties(prefix = "honghao")
public class ParamConfig {
    /**
     * 名字
     */
    private String name;

    @PostConstruct
    void beforeInfo(){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>"+name);
    }
}
