package com.honghao.cloud.userapi.spring;

import com.honghao.cloud.userapi.spring.bean.AppleA;
import com.honghao.cloud.userapi.spring.bean.AppleB;
import com.honghao.cloud.userapi.spring.bean.AppleC;
import com.honghao.cloud.userapi.spring.bean.AppleE;
import com.honghao.cloud.userapi.spring.filter.MyTypeFilter;
import com.honghao.cloud.userapi.spring.importin.AppleBTest;
import com.honghao.cloud.userapi.spring.importin.ImportSelectTest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.*;

/**
 * @author chenhonghao
 * @date 2020-01-13 20:47
 */
@Configuration
//导入类
@Import(value = {ImportSelectTest.class, AppleA.class, AppleBTest.class, AppleC.class})
//指定要扫描的包，并注册到ioc容器中,并且按照规则排除指定的组件
@ComponentScan(basePackages = "com.honghao.cloud.userapi.spring",includeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Service.class}),
        @ComponentScan.Filter(type = FilterType.CUSTOM,classes = {MyTypeFilter.class})
})

//导入了一个MapperScannerConfigurer 的bean定义
//@MapperScan(value = "com.honghao.cloud.userapi.domain.mapper")
//@EnableEcho(packages = "com.honghao.cloud.userapi.spring")
public class TestConfig {

    @Bean
    @ConditionalOnClass(AppleB.class)
    public AppleE appleE(){
        return new AppleE();
    }
}
