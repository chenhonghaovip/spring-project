package com.honghao.cloud.userapi.spring.bean;

import org.springframework.scheduling.annotation.Async;

/**
 * @author chenhonghao
 * @date 2020-03-28 15:51
 */
public class AsyncTest {

    @Async
    public void test(){
        System.out.println("111");
    }
}
