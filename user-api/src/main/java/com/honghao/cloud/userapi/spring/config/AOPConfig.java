package com.honghao.cloud.userapi.spring.config;

import com.honghao.cloud.userapi.spring.TestAspect;
import com.honghao.cloud.userapi.spring.bean.Fox;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author chenhonghao
 * @date 2020-03-20 15:51
 */
@EnableAspectJAutoProxy
@Configuration
public class AOPConfig {

    @Bean
    public TestAspect testAspect(){
        return new TestAspect();
    }

    @Bean
    public Fox fox(){
        return new Fox();
    }
}
