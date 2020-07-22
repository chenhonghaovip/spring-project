package com.honghao.cloud.accountapi.common.factory;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * @author chenhonghao
 * @date 2020-07-21 14:42
 */
public class MyFutureTask<T> implements Runnable{
    Callable<T> callable;
    T result;
    volatile String state = "New";
    LinkedBlockingQueue<Thread> waiters;

    public MyFutureTask(Callable<T> callable) {
        this.callable = callable;
    }

    T get(){
        if ("End".equals(state)){
            return result;
        }
        while (!"End".equals(state)){
            waiters.add(Thread.currentThread());
            LockSupport.park();
        }
        return result;
    }


    @Override
    public void run() {
        try {
            result = callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            state = "End";
        }
        Thread poll = waiters.poll();
        while (poll!=null){
            LockSupport.unpark(poll);
            poll = waiters.poll();
        }
    }
}
