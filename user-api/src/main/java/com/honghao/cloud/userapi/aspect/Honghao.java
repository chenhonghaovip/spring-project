package com.honghao.cloud.userapi.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author chenhonghao
 * @date 2020-05-27 15:31
 */
@Slf4j
@Component
public class Honghao implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @RabbitListener(queues = "honghao_queue")
    public void test(String s){
        DTO dto = JSON.parseObject(s,DTO.class);
        String beanName = dto.getBeanName();
        try {
            Class<?> aClass = Class.forName(beanName);
            Object bean = applicationContext.getBean(aClass);
            Optional<Method> any = Arrays.stream(aClass.getDeclaredMethods()).filter(each -> each.getName().equals(dto.getMethodName())).findAny();
            if (any.isPresent()){
                any.get().invoke(bean,dto.getContext());
            }
        } catch (IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            log.error(e.getMessage());
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
