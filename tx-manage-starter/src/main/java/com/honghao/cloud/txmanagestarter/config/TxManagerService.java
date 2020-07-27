package com.honghao.cloud.txmanagestarter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author chenhonghao
 * @date 2020-07-13 15:09
 */
@Configuration
// 实现可插拔的特性
@ConditionalOnBean(TxManagerAutoConfiguration.class)
public class TxManagerService {
    private DataSource dataSource;

    public TxManagerService(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
