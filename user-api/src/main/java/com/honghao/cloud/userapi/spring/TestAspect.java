package com.honghao.cloud.userapi.spring;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * 切面类
 *
 * @author chenhonghao
 * @date 2020-03-20 15:52
 */
@Aspect
public class TestAspect {

    @Before("execution(* com.honghao.cloud.userapi.spring.bean..*(..))")
    public void logStart(){
        System.out.println("log start");
    }

    @After("execution(* com.honghao.cloud.userapi.spring.bean..*(..))")
    public void logEnd(){
        System.out.println("log end");
    }
}
