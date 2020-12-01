package com.honghao.cloud.userapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
/**
 * @author chenhonghao
 * @date 2019-7-17
 */
@EnableHystrix
@EnableHystrixDashboard
@EnableFeignClients
//@EnableTransactionManagement
@EnableEurekaClient
@SpringBootApplication
@MapperScan("com.honghao.cloud.userapi.domain.mapper")
public class UserApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApiApplication.class, args);
    }


}
