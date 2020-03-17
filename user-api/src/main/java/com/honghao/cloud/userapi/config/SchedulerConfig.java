package com.honghao.cloud.userapi.config;

import com.honghao.cloud.userapi.factory.QuartzFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定时调度配置
 *
 * @author chenhonghao
 * @date 2020-03-17 15:12
 */
@Configuration
public class SchedulerConfig {

    @Bean
    public Scheduler scheduler(){
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        return QuartzFactory.getInstance();
    }
}
