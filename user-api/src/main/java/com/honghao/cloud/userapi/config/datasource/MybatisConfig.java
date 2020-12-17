package com.honghao.cloud.userapi.config.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * mybatis配置
 *
 * @author chenhonghao
 * @date 2019-12-26 14:27
 */
@Configuration
public class MybatisConfig {

    @Scope(value = "prototype")
    @Bean(name = "mybatis.config")
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public org.apache.ibatis.session.Configuration globalConfiguration() {
        return new org.apache.ibatis.session.Configuration();
    }

}
