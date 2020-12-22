package com.honghao.cloud.userapi.timer;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author chenhonghao
 * @date 2020-03-06 11:26
 */
public class PrintWordsJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String name = jobDataMap.getString("name");
        String group = jobDataMap.getString("group");
        String printTime = new SimpleDateFormat("yy-MM-dd HH-mm-ss").format(new Date());
        System.out.println("PrintWordsJob--" + name + "--" + group + " start at:" + printTime + ", prints: Hello Job-" + new Random().nextInt(100));

    }
}
