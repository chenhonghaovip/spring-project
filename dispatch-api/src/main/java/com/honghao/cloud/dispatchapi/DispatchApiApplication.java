package com.honghao.cloud.dispatchapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author chenhonghao
 * @date 2019-7-17
 */
@EnableFeignClients
@SpringBootApplication
@EnableSwagger2
@MapperScan("com.honghao.cloud.dispatchapi.domain.mapper")
public class DispatchApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DispatchApiApplication.class, args);
    }

}
