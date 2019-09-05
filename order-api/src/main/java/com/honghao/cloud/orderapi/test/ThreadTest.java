package com.honghao.cloud.orderapi.test;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程wait测试
 *
 * @author chenhonghao
 * @date 2019-08-27 13:28
 */
@Slf4j
public class ThreadTest {
    private volatile boolean go = false;

    public static void main(String args[]) throws InterruptedException {
        final ThreadTest test = new ThreadTest();

        Runnable waitTask = () -> {
            try {
                test.shouldGo();
            } catch (InterruptedException ex) {
                log.info("{}", ex);
            }
            System.out.println(Thread.currentThread() + " finished Execution");
        };
        Runnable notifyTask = () -> {
            test.go();
            System.out.println(Thread.currentThread() + " finished Execution");
        };
        //will wait
        Thread t1 = new Thread(waitTask, "WT1");
        Thread t2 = new Thread(waitTask, "WT2");
        Thread t3 = new Thread(waitTask, "WT3");
        //will notify
        Thread t4 = new Thread(notifyTask, "NT1");
        //starting all waiting thread
        t1.start();
        t2.start();
        t3.start();
        //pause to ensure all waiting thread started successfully
        Thread.sleep(200);
        //starting notifying thread
        t4.start();
    }
    /*
     *
     */

    /**
     * wait and notify can only be called from synchronized method or bock
     *
     * @throws InterruptedException in
     */
    private synchronized void shouldGo() throws InterruptedException {
        System.out.println(Thread.currentThread()
                + " insert into shouldGo()");
        while (!go) {
            System.out.println(Thread.currentThread()
                    + " is going to wait on this object");
            wait(); //release lock and reacquires on wakeup
            System.out.println(Thread.currentThread() + " is woken up");
        }
        //resetting condition
        go = false;
    }

    /**
     * both shouldGo() and go() are locked on current object referenced by "this" keyword
     */
    private synchronized void go() {
        while (!go) {
            System.out.println(Thread.currentThread()
                    + " is going to notify all or one thread waiting on this object");
            //making condition true for waiting thread
            go = true;
            //notify(); // only one out of three waiting thread WT1, WT2,WT3 will woke up
            notifyAll(); // all waiting thread  WT1, WT2,WT3 will woke up
        }

    }
}
