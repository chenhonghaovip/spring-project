package com.honghao.cloud.orderapi.aspect;

import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.base.RetryException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 重试切面处理
 *
 * @author chenhonghao
 * @date 2020-09-29 11:57
 */
@Slf4j
@Aspect
@Component
public class TryAgainAspect {

    @Pointcut("@annotation(TryAgain)")
    public void  aspect(){}

    @Around("aspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        // 获取拦截的方法名
        MethodSignature msig = (MethodSignature) joinPoint.getSignature();
        // 返回被织入增加处理目标对象
        Object target = joinPoint.getTarget();
        // 为了获取注解信息
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

        // 获取设置的乐观锁最大重试次数
        TryAgain annotation = currentMethod.getAnnotation(TryAgain.class);
        int maxValue = annotation.value();

        int retryTimes = 0;
        do {
            log.info("msg:--------------------"+Thread.currentThread().getName());
            retryTimes++;
            try {
                return joinPoint.proceed();
            } catch (RetryException e) {
                log.error(e.getErrorMsg());
            }
        }while (maxValue>=retryTimes);
        log.error("乐观锁重试失败");
        return BaseResponse.error();
    }
}
