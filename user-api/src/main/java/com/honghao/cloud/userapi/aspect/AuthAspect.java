package com.honghao.cloud.userapi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 自定义注解切面
 *
 * @author chenhonghao
 * @date 2019-07-18 17:36
 */
@Slf4j
@Component
@Aspect
public class AuthAspect {
    @Pointcut("@annotation(Auth)")
    public void controller(){
    }

    @Before("controller()")
    public void getInfo(JoinPoint joinPoint){
        log.info("before getInfo started >>>>>");
    }

    @After("controller()")
    public void afterInfo(JoinPoint joinPoint){
        log.info("after getInfo started<<<<<<");
    }

    /**
     * 环绕增强
     * @param joinPoint joinPoint
     * @return Object
     * @throws Throwable 异常
     */
    @Around("controller()")
    public Object run2(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取方法参数值数组
        Object[] args = joinPoint.getArgs();
        //得到其方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取方法参数类型数组
        Class[] paramTypeArray = methodSignature.getParameterTypes();
        log.info("请求参数为{}",args);
        for (Object arg : args) {
            log.info("arg = {}",arg);
        }
//        JSONObject jsonObject = JSON.parseObject(args[0].toString());
//        String kappId = jsonObject.getString("kappId");
//        log.info("kappId = {}",kappId);
        //动态修改其参数
        //注意，如果调用joinPoint.proceed()方法，则修改的参数值不会生效，必须调用joinPoint.proceed(Object[] args)
        Object result = joinPoint.proceed(args);
        log.info("响应结果为{}",result);
        //如果这里不返回result，则目标对象实际返回值会被置为null
        return result;
    }
}
