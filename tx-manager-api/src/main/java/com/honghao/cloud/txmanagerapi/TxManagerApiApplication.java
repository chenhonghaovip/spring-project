package com.honghao.cloud.txmanagerapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author CHH
 */
@EnableTransactionManagement
@SpringBootApplication
@MapperScan("com.honghao.cloud.txmanagerapi.domain.mapper")
public class TxManagerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxManagerApiApplication.class, args);
    }

}
