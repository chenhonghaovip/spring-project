package com.honghao.cloud.userapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author chenhonghao
 * @date 2019-7-17
 */
@EnableHystrix
@EnableCircuitBreaker
@EnableFeignClients
@EnableTransactionManagement
@EnableEurekaClient
@EnableSwagger2
@SpringBootApplication
@MapperScan("com.honghao.cloud.userapi.domain.mapper")
public class UserApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApiApplication.class, args);
    }

}
