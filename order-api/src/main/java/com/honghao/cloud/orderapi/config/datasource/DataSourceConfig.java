package com.honghao.cloud.orderapi.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author chenhonghao
 * @date 2019-12-20 09:47
 */
@Configuration
public class DataSourceConfig {
    @Bean("dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }



    @Bean("dataSourceProxy")
    public DataSourceProxy dataSourceProxy(@Qualifier("dataSource")DataSource dataSource) {
        DataSourceProxy proxy=new DataSourceProxy(dataSource);
        return proxy;
    }
}
