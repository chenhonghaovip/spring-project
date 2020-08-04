package com.honghao.cloud.basic.common.base.factory;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 定时调度工程
 *
 * @author chenhonghao
 * @date 2020-03-06 11:01
 */
@Slf4j
public class QuartzFactory {
    private static ConcurrentHashMap<String, JobDetail> jobDetaiMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Trigger> triggerMap = new ConcurrentHashMap<>();

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
                        scheduler.start();
                    } catch (SchedulerException e) {
                        log.error(e.getMessage());
                    }
                }
            }
        }
        return scheduler;
    }

    /**
     * 构建jobDetail
     * @param clazz 实现类
     * @param map 参数
     * @param name name
     * @param group group
     * @param <T> T
     * @return JobDetail
     */
    public static <T extends Job> JobDetail buildJobDetail(Class<T> clazz, Map<String,String> map, String name, String group){
        JobDetail jobDetail = JobBuilder.newJob(clazz)
                .setJobData(new JobDataMap(map))
                .withIdentity(name, group).build();
        if (jobDetaiMap.putIfAbsent(name,jobDetail)!=null){
            jobDetail = jobDetaiMap.get(name);
        }
        return jobDetail;
    }

    /**
     * 构建调度器
     * @return Trigger
     */
    public static Trigger buildTrigger(String name, String group){
        // 构建Trigger实例,每隔1s执行一次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
                .startNow()//立即生效
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(1)
                        .withRepeatCount(3)).build();

        if (triggerMap.putIfAbsent(name,trigger)!=null){
            trigger = triggerMap.get(name);
        }
        return trigger;
    }

}
