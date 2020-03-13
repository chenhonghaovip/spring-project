package com.honghao.cloud.userapi.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 参数类配置
 *
 * @author chenhonghao
 * @date 2019-07-20 01:08
 */
@Data
@Component
public class ParamTestConfig {
    /**
     * 名字
     */
    @Value("${test.testName}")
    private String testName;

    @PostConstruct
    void beforeInfo(){
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>"+testName);
    }

    @PreDestroy
    void preDestroy(){
//        System.out.println("<<<<<<<<<<<<<<<<<<<<"+testName);
    }
}
