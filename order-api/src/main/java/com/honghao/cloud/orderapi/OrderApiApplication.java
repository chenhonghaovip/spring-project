package com.honghao.cloud.orderapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author chenhonghao
 * @date 2019-7-17
 */
@EnableTransactionManagement
@EnableFeignClients
@EnableEurekaClient
@MapperScan("com.honghao.cloud.orderapi.domain.mapper")
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class OrderApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApiApplication.class, args);
    }

}
