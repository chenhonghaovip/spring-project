package com.honghao.cloud.userapi.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author chenhonghao
 * @date 2020-07-20 15:25
 */
@Aspect
@Component
public class LogInfoAspect {
    private Logger logger = LoggerFactory.getLogger(LogInfoAspect.class);
    @Pointcut("@annotation(LogInfo)")
    public void point(){
    }

    @SuppressWarnings("unchecked")
    @Around("point()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 请求参数
        Signature sig = joinPoint.getSignature();

        MethodSignature msig = (MethodSignature) sig;
        // 获取目标类
        Class type = sig.getDeclaringType();

        // 获取目标方法
        Method declaredMethod = type.getDeclaredMethod(msig.getName(), msig.getParameterTypes());
        Object[] args = joinPoint.getArgs();
        logger.info("类:{}--方法:{}--请求参数:{}",type,declaredMethod,args);
        Object proceed = joinPoint.proceed(args);
        logger.info("类:{}--方法:{}--请求参数:{}--请求结果:{}",type,declaredMethod,args, proceed);
        return proceed;
    }

}
