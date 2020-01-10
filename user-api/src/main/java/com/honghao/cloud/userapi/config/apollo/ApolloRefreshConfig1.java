package com.honghao.cloud.userapi.config.apollo;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chenhonghao
 * @date 2020-01-10 17:28
 */
@Slf4j
@Component
public class ApolloRefreshConfig1 {
    @Resource
    private ApplicationContext applicationContext;

    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent) {
        refreshTaskScheduleProperties(changeEvent);
    }
    private void refreshTaskScheduleProperties(ConfigChangeEvent changeEvent) {
        log.info("Refreshing TaskSchedule properties!");

        // 更新相应的bean的属性值，主要是存在@ConfigurationProperties注解的bean
        this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));

        log.info("TaskSchedule properties refreshed!");
    }

}
