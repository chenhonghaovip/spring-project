package com.honghao.cloud.userapi.test;

import org.omg.CORBA.ObjectHolder;

/**
 * @author chenhonghao
 * @date 2020-08-12 17:00
 */
public class JVMTest {
    static class Test{
        static ObjectHolder staticObject = new ObjectHolder();
        ObjectHolder instanceObj = new ObjectHolder();
        public void foo(){
            ObjectHolder objectHolder = new ObjectHolder();
            System.out.println("done");
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.foo();
    }
}
