package com.honghao.cloud.userapi.timer;

import com.honghao.cloud.userapi.config.QuartzFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.quartz.*;

import java.util.concurrent.TimeUnit;

/**
 * @author chenhonghao
 * @date 2020-03-06 11:11
 */
@Slf4j
public class MyScheduler {
    public static void main(String[] args) {
        MyScheduler myScheduler = new MyScheduler();
        myScheduler.scheduler();
    }

    @Test
    public void scheduler1() {
        System.out.println("111");
    }

    public void scheduler() {
        // 1、创建调度器Scheduler
        Scheduler scheduler = QuartzFactory.getInstance();

        // 2、创建JobDetail实例，并与PrintWordsJob类绑定(Job执行内容)
        JobDetail jobDetail = JobBuilder.newJob(PrintWordsJob.class)
                .withIdentity("job1", "group1").build();

        // 3、构建Trigger实例,每隔1s执行一次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1")
                .startNow()//立即生效
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)//每隔1s执行一次
                        .repeatForever()).build();//一直执行

        try {
            scheduler.scheduleJob(jobDetail, trigger);
            System.out.println("--------scheduler start ! ------------");
            scheduler.start();
            TimeUnit.MINUTES.sleep(1);
            scheduler.shutdown();
            System.out.println("--------scheduler shutdown ! ------------");
        } catch (SchedulerException | InterruptedException e) {
            e.printStackTrace();
        }


    }
}
