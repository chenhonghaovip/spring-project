package com.honghao.cloud.userapi.spring;

import org.aspectj.lang.annotation.*;

/**
 * 切面类
 *
 * @author chenhonghao
 * @date 2020-03-20 15:52
 */
@Aspect
public class TestAspect {

//    @Before("execution(*s com.honghao.cloud.userapi.spring.bean..*(..))")
//    public void logStart(){
//        System.out.println("log start");
//    }
//
//    @After("execution(* com.honghao.cloud.userapi.spring.bean..*(..))")
//    public void logEnd(){
//        System.out.println("log end");
//    }

    @Pointcut("execution(* com.honghao.cloud.userapi.spring.bean..*(..))")
    public void point(){

    }
    @Before("point()")
    public void before() {
        System.out.println("this is from HelloAspect#before...");
    }

    @After("point()")
    public void after() {
        System.out.println("this is from HelloAspect#after...");
    }

    @AfterReturning("point()")
    public void afterReturning() {
        System.out.println("this is from HelloAspect#afterReturning...");
    }

    @AfterThrowing("point()")
    public void afterThrowing() {
        System.out.println("this is from HelloAspect#afterThrowing...");
    }
}
