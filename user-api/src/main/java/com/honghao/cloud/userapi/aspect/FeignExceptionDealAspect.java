package com.honghao.cloud.userapi.aspect;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.userapi.listener.rabbitmq.producer.MessageSender;
import com.honghao.cloud.userapi.utils.JedisOperator;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.retry.RetryException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * feign接口超时处理切面
 *
 * @author chenhonghao
 * @date 2020-05-27 15:01
 */
@Slf4j
@Aspect
@Component
public class FeignExceptionDealAspect {
    @Resource
    private JedisOperator jedisOperator;
    @Resource
    private MessageSender messageSender;

    @Pointcut("@annotation(FeignExceptionDeal)")
    public void point(){
    }

    @Around("point()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 请求参数
        Object[] args = joinPoint.getArgs();
        try {
            joinPoint.proceed(args);

        } catch (RetryException | HystrixRuntimeException re){
            log.error(re.getMessage());
            Signature sig = joinPoint.getSignature();
            MethodSignature msig = (MethodSignature) sig;
            String beanName = joinPoint.getSignature().getDeclaringType().getName();
            Object target = joinPoint.getTarget();
            Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
            DTO dto = DTO.builder().t(args[0].getClass().newInstance()).context(args).methodName(currentMethod.getName()).beanName(beanName).build();

            String key = beanName+currentMethod.getName()+args[0].toString();
            System.out.println(key);
            if (jedisOperator.incr(key)>5){
                jedisOperator.del(key);
                //入库
                System.out.println("============================ruhuchuli");
            } else {
                jedisOperator.expire(key,30);
                System.out.println("-----------------------------");
                messageSender.publicQueueProcessing(JSON.toJSONString(dto), "honghao_queue");
            }
            return;
        } catch (Throwable throwable) {
            log.error(throwable.getMessage());
        }
        System.out.println("success");
    }
}
