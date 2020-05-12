package com.honghao.cloud.userapi.controller;

import com.honghao.cloud.userapi.factory.ExecutorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenhonghao
 * @date 2020-05-11 21:55
 */
@RestController
@RequestMapping("/test")
public class Test {
    private static Logger logger = LoggerFactory.getLogger(Test.class);
    private static ThreadPoolExecutor parent = ExecutorFactory.buildThreadPoolExecutor(10,100,"odoofd");
    private static ThreadPoolExecutor child = ExecutorFactory.buildThreadPoolExecutor(10,100,"odoofd");
    @Resource
    private DataSourceTransactionManager dataSourceTransactionManager;
    private static Set<Integer> set = new HashSet<>();
    private static ConcurrentHashMap<Integer,Integer> concurrentHashMap = new ConcurrentHashMap<>();

    @org.junit.Test
    public void test(){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(500);

        AtomicInteger atomicInteger = new AtomicInteger(1);
        for (int i = 0; i < 100000; i++) {
            new Thread(new CyclicBarrierThread(cyclicBarrier,atomicInteger.getAndIncrement())).start();
        }
    }


    private static class CyclicBarrierThread implements Runnable {
        CyclicBarrier cyclicBarrier;

        //任务序号
        int taskNum;

        private void test1(){
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            concurrentHashMap.put(taskNum,taskNum);
            child.execute(()-> {
                if (!concurrentHashMap.keySet().contains(taskNum)){
                    System.out.println("1111111111111111111==="+taskNum);
                }
            });

        }

        CyclicBarrierThread(CyclicBarrier cyclicBarrier,final int taskNum) {
            this.cyclicBarrier = cyclicBarrier;
            this.taskNum = taskNum;
        }

        @Override
        public void run() {
            test1();
        }
    }
}
