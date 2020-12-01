package com.honghao.cloud.message.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 消息服务配置中心
 *
 * @author chenhonghao
 * @date 2020-11-26 09:41
 */
@Data
@Component
@ConfigurationProperties(prefix = "message")
public class MessageConfig {
    private String name;

    private String temp;
}
