package com.honghao.cloud.txmanagestarter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author chenhonghao
 * @date 2020-07-13 13:17
 */
@Configuration
public class TxManagerAutoConfiguration {

    private DataSource dataSource;


    public TxManagerAutoConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public TxManagerService txManagerService(){
        return new TxManagerService(dataSource);
    }
}
