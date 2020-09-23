package com.honghao.cloud.accountapi.config.apollo;

import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * apollo自动刷新配置
 *
 * @author chenhonghao
 * @date 2020-01-02 16:34
 */
@Slf4j
@Component
public class ApolloRefreshConfig implements ApplicationContextAware {
    private static final String LOG_PREFIX = "log.level";
    private ApplicationContext applicationContext;
    @Resource
    private LoggingSystem loggingSystem;

    @ApolloConfigChangeListener(value = {"application","BusinessDataSource"})
    public void onChange(ConfigChangeEvent changeEvent) {
        refreshTaskScheduleProperties(changeEvent);
    }
    private void refreshTaskScheduleProperties(ConfigChangeEvent changeEvent) {
        log.info("Refreshing TaskSchedule properties!");

        // 更新相应的bean的属性值，主要是存在@ConfigurationProperties注解的bean
        this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));

        // 监听指定字段的修改，实现动态修改日志级别的功能
        if (changeEvent.changedKeys().contains(LOG_PREFIX)){
            ConfigChange change = changeEvent.getChange(LOG_PREFIX);
            String newValue = change.getNewValue();
            LogLevel level = LogLevel.valueOf(newValue.toUpperCase());
            loggingSystem.setLogLevel("",level);
        }
        log.info("TaskSchedule properties refreshed!");
    }


    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
