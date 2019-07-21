package com.honghao.cloud.userapi.facade.impl;

import com.alibaba.fastjson.JSONObject;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import com.honghao.cloud.userapi.facade.WaybillBcListFacade;
import com.honghao.cloud.userapi.interceptor.UserInfoHolder;
import com.honghao.cloud.userapi.listener.rabbitmq.producer.MessageSender;
import com.honghao.cloud.userapi.service.WaybillBcListService;
import com.honghao.cloud.userapi.task.AsyncTask;
import com.honghao.cloud.userapi.utils.JedisOperator;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Override
    @HystrixCommand(fallbackMethod = "createUserFallback")
    public Boolean createUser(String data) {
        JSONObject jsonObject1=JSONObject.parseObject(data);
        String batchId=jsonObject1.getString("batchId");
        log.info("start create user ,requestParam:{}",data);
        String agentNo = UserInfoHolder.getOperator().getAgentNo();
        messageSender.pushInfoUser("userinfo");
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("value","chenwenliang");
        messageSender.delayDoAction(jsonObject);
        jedisOperator.set(DICTIONARY+batchId,batchId);
        asyncTask.sendInfo();
        WaybillBcList waybillBcList=new WaybillBcList();
        waybillBcListService.createUser(waybillBcList);
        return null;
    }

    public Boolean createUserFallback(String data){
        log.info("服务熔断，返回默认值！");
        return false;
    }
}
