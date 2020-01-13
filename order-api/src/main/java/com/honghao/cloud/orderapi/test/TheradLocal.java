package com.honghao.cloud.orderapi.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenhonghao
 * @date 2019-10-27 20:37
 */
@Slf4j
public class TheradLocal {
    public static void main(String[] args) {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("hello world");
        String name = threadLocal.get();
        System.out.println(name);
    }
}
