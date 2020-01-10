package com.honghao.cloud.userapi.test.springtest;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 用户类
 *
 * @author chenhonghao
 * @date 2020-01-08 10:07
 */
@Data
@Component
@ConfigurationProperties(prefix = "user.info")
public class User {
    private String name;
    private Integer age;
}
