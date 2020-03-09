package com.honghao.cloud.userapi.timer;

import org.quartz.Job;
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
        String printTime = new SimpleDateFormat("yy-MM-dd HH-mm-ss").format(new Date());
        System.out.println("PrintWordsJob start at:" + printTime + ", prints: Hello Job-" + new Random().nextInt(100));

    }
}
