package com.honghao.cloud.userapi.test.face;

import io.reactivex.subjects.ReplaySubject;

/**
 * @author chenhonghao
 * @date 2020-08-12 19:58
 */
public class Test02 {
    public static synchronized void test() {
        System.out.println("static synchronized");
    }

    public static void main(String[] args) {
        ReplaySubject<Integer> source = ReplaySubject.create();
        //ReplaySubject：接收到所有的数据，包括订阅之前的所有数据和订阅之后的所有数据。
        // It will get 1, 2, 3, 4
        source.subscribe(integer -> System.out.println("pre=" + integer));
        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        source.onNext(4);
        source.onComplete();
        // It will also get 1, 2, 3, 4 as we have used replay Subject
        source.subscribe(integer -> System.out.println("after=" + integer));
//        source.subscribe(getSecondObserver());
    }

    public void syncTest() {
        synchronized (Test02.class) {
            System.out.println("syncTest");
        }
    }

    public void syncBlock() {
        synchronized (this) {
            System.out.println("hello block");
        }
    }

    public synchronized void syncMethod() {
        System.out.println("hello method");
    }
}
