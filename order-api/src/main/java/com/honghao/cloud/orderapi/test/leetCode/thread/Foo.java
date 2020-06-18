package com.honghao.cloud.orderapi.test.leetCode.thread;

/**
 * @author chenhonghao
 * @date 2020-06-18 09:50
 */
public class Foo {
    boolean first = false;
    boolean second = false;
    Object object = new Object();
    public Foo() {

    }

    public void first(Runnable printFirst) {
        synchronized (object){
            first = true;
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
        }

    }

    public void second(Runnable printSecond)  {
        synchronized (object){
            while (!first){
                object.notifyAll();
            }
                // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            second = true;

        }
    }

    public void third(Runnable printThird) {
        synchronized (object){
            while (!second){
                object.notifyAll();
            }
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        }

    }
}
