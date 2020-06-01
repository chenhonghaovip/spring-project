package com.honghao.cloud.userapi.aspect;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.domain.entity.ErrMsg;
import com.honghao.cloud.userapi.domain.mapper.master.ErrMsgMapper;
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
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Objects;

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
    private ErrMsgMapper errMsgMapper;
    @Resource
    private JedisOperator jedisOperator;
    @Resource
    private MessageSender messageSender;

    @Pointcut("@annotation(FeignExceptionDeal)")
    public void point(){
    }

    @SuppressWarnings("unchecked")
    @Around("point()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 请求参数
        Object[] args = joinPoint.getArgs();
        try {
            return joinPoint.proceed(args);
        } catch (RetryException | HystrixRuntimeException | IOException re){
            Signature sig = joinPoint.getSignature();

            MethodSignature msig = (MethodSignature) sig;
            // 获取目标类
            Class type = sig.getDeclaringType();
            String beanName = type.getName();

            // 获取目标方法
            Method declaredMethod = type.getDeclaredMethod(msig.getName(), msig.getParameterTypes());

            // 获取目标方法上的注解（只处理非GetMapping类型的http请求）
            GetMapping annotation = declaredMethod.getAnnotation(GetMapping.class);
            if (Objects.nonNull(annotation)){
                return BaseResponse.success();
            }
            FeignExceptionDeal feignExceptionDeal = declaredMethod.getAnnotation(FeignExceptionDeal.class);
            int times = feignExceptionDeal.retryTimes();

            DTO dto = DTO.builder()
                    .paramType(args[0].getClass().getName())
                    .context(args)
                    .methodName(declaredMethod.getName())
                    .beanName(beanName).build();
            String key = beanName+declaredMethod.getName()+args[0].toString();
            if (jedisOperator.incr(key)>times){
                jedisOperator.del(key);
                //入库
                ErrMsg errMsg = ErrMsg.builder().createDate(new Date()).msg(JSON.toJSONString(dto)).build();
                errMsgMapper.insertSelective(errMsg);
                System.out.println("============================ruhuchuli");
            } else {
                jedisOperator.expire(key,30);
                System.out.println("-----------------------------");
                messageSender.publicQueueProcessing(JSON.toJSONString(dto), "honghao_queue");
            }
        } catch (Throwable throwable) {
            log.error(throwable.getMessage());
        }
        return BaseResponse.error();
    }
}
