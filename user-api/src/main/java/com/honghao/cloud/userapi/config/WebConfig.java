package com.honghao.cloud.userapi.config;

import com.honghao.cloud.userapi.interceptor.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 拦截器注册
 *
 * @author chenhonghao
 * @date 2019-07-18 20:12
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    /**
     * 添加拦截器信息
     * @param registry register
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor());
        super.addInterceptors(registry);
    }

    /**
     * 注入bean,交由容器进行管理
     * @return UserInterceptor对象
     */
    @Bean
    public UserInterceptor userInterceptor(){
        return new UserInterceptor();
    }
}
