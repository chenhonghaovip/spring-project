package com.honghao.cloud.userapi.test.face.thread;

import com.honghao.cloud.basic.common.factory.ThreadPoolFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 计数信号量测试
 *
 * @author chenhonghao
 * @date 2020-05-27 10:48
 */
@Slf4j
public class SemaphoreTest {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(3,3,"test_");
        AtomicInteger atomicInteger = new AtomicInteger(1);
        //信号量，只允许 3个线程同时访问
        Semaphore semaphore = new Semaphore(1);
        threadPoolExecutor.submit(()->{
            while (true){
                try {
                    if (atomicInteger.get()%3==1 && atomicInteger.get()<=99){
                        semaphore.acquire();
                        System.out.println(Thread.currentThread().getName()+"==="+atomicInteger.getAndIncrement());
                        semaphore.release();
                    }
                    if (atomicInteger.get()>99){
                        return ;
                    }
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            }
        });
        threadPoolExecutor.submit(()->{
            while (true){
                try {
                    if (atomicInteger.get()%3==2 && atomicInteger.get()<=99){
                        semaphore.acquire();
                        System.out.println(Thread.currentThread().getName()+"==="+atomicInteger.getAndIncrement());
                        semaphore.release();
                    }
                    if (atomicInteger.get()>99){
                        return ;
                    }
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            }
        });
        threadPoolExecutor.submit(()->{
            while (true){
                try {
                    if (atomicInteger.get()%3==0 && atomicInteger.get()<=99){
                        semaphore.acquire();
                        System.out.println(Thread.currentThread().getName()+"==="+atomicInteger.getAndIncrement());
                        semaphore.release();
                    }
                    if (atomicInteger.get()>99){
                        return ;
                    }
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            }
        });
    }
}
