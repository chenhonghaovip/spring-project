package com.honghao.cloud.accountapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 数据同步配置
 *
 * @author chenhonghao
 * @date 2020-09-21 11:19
 */
@Data
@ConfigurationProperties(prefix = "data.synchronized")
public class DataSynchronizationConfig {
    private String canalAddress = "127.0.0.1";

    private int port = 11111;

    private String destination = "example";

    private String userName = "canal";

    private String password = "canal";
}
