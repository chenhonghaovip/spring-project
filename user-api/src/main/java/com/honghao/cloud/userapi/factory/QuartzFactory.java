package com.honghao.cloud.userapi.factory;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 定时调度工程
 *
 * @author chenhonghao
 * @date 2020-03-06 11:01
 */
@Slf4j
public class QuartzFactory {
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private static volatile Scheduler scheduler;

    private QuartzFactory() {
    }
    public static Scheduler getInstance(){
        if (scheduler==null){
            synchronized (QuartzFactory.class){
                if (scheduler==null){
                    try {
                        scheduler = schedulerFactory.getScheduler();
                    } catch (SchedulerException e) {
                        log.error(e.getMessage());
                    }
                }
            }
        }
        return scheduler;
    }
}
