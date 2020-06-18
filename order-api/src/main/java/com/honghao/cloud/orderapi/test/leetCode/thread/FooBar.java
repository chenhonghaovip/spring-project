package com.honghao.cloud.orderapi.test.leetCode.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenhonghao
 * @date 2020-06-18 10:46
 */
public class FooBar {
    private int n;
    private boolean flag = false;
    private ReentrantLock reentrantLock = new ReentrantLock();

    Semaphore semaphore = new Semaphore(1);

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            reentrantLock.lock();
            try {
                if (!flag){
                    // printFoo.run() outputs "foo". Do not change or remove this line.
                    printFoo.run();
                    flag = true;
                }else {
                    i--;
                }
            } finally {
                reentrantLock.unlock();
            }

        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            reentrantLock.lock();
            try {
                if (flag){
                    printBar.run();
                    flag = false;
                }else {
                    i--;
                }
            } finally {
                reentrantLock.unlock();
            }

        }
    }
}
