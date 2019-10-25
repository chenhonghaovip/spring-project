package com.honghao.cloud.userapi.facade.impl;

import com.alibaba.fastjson.JSONObject;
import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.client.OrderClient;
import com.honghao.cloud.userapi.config.ThreadPoolInitConfig;
import com.honghao.cloud.userapi.dto.request.EventDTO;
import com.honghao.cloud.userapi.facade.WaybillBcListFacade;
import com.honghao.cloud.userapi.interceptor.UserInfoHolder;
import com.honghao.cloud.userapi.listener.event.EventDemo;
import com.honghao.cloud.userapi.listener.rabbitmq.producer.MessageSender;
import com.honghao.cloud.userapi.service.WaybillBcListService;
import com.honghao.cloud.userapi.task.AsyncTask;
import com.honghao.cloud.userapi.utils.DozerUtils;
import com.honghao.cloud.userapi.utils.JedisOperator;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 订单操作实现类
 *
 * @author chenhonghao
 * @date 2019-07-18 17:28
 */
@Slf4j
@Service
public class WaybillBcListFacadeImpl implements WaybillBcListFacade {
    private static final String DICTIONARY="dictionart_";

    @Resource
    private WaybillBcListService waybillBcListService;
    @Resource
    private MessageSender messageSender;
    @Resource
    private AsyncTask asyncTask;
    @Resource
    private JedisOperator jedisOperator;
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;
    @Resource
    private OrderClient orderClient;

    @Override
    @HystrixCommand(fallbackMethod = "createUserFallback")
    public Boolean createUser(String data) {
        JSONObject jsonObject1=JSONObject.parseObject(data);
        String batchId=jsonObject1.getString("batchId");

        log.info("{}", UserInfoHolder.getOperator());
        String agentNo = UserInfoHolder.getOperator().getAgentNo();
        log.info(agentNo);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("value","chenwenliang");

        jedisOperator.set(DICTIONARY+batchId,batchId);
        asyncTask.sendInfo();
        messageSender.sendMessage(jsonObject);
        EventDTO eventDTO= EventDTO.builder().code(1)
                .desc("chenhonghao").build();

        EventDemo eventListener=new EventDemo(this,eventDTO);
        applicationEventPublisher.publishEvent(eventListener);

        log.info("开始订单服务的接口调用");
        BaseResponse<String> baseResponse = orderClient.createUser("dddddd");
        if (baseResponse.isResult()){
            log.info("接口调用成功");
            System.out.println(baseResponse.getData());
        }else {
            log.info("接口调用失败");
        }

        return null;
    }

    @Override
    public Boolean createUser1(String data) {
        DozerBeanMapper dozerBeanMapper = DozerUtils.createDozer();
        messageSender.testQueue("name is chenhonghao");
        ThreadPoolExecutor executor= ThreadPoolInitConfig.build("create");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "xing is chen";
        },executor);

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "name is honghao";
        },executor);

        CompletableFuture.allOf(future, future1)
                .whenCompleteAsync((aVoid, throwable) -> {
                        String name= null;
                        String xing= null;
                        try {
                            name = future1.get();
                            xing = future.get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        System.out.println(name+" and "+xing);
                },executor);
        log.info("start time is :{}",System.currentTimeMillis());
        return null;
    }

    @Override
    public Boolean createUser2(String data) {
//        messageSender.pushInfoUser("nnnnnnnnnnnnn");
        messageSender.outQueue("广播消息");
        return false;

    }

    /**
     * 服务降级
     * @param data data
     * @return Boolean
     */
    public Boolean createUserFallback(String data){
        log.info("服务熔断，返回默认值！");
        return false;
    }
}
