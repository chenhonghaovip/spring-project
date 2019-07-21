package com.honghao.cloud.userapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.honghao.cloud.userapi.aspect.Auth;
import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.facade.WaybillBcListFacade;
import com.honghao.cloud.userapi.interceptor.UserInfoHolder;
import com.honghao.cloud.userapi.listener.rabbitmq.producer.MessageSender;
import com.honghao.cloud.userapi.task.AsyncTask;
import com.honghao.cloud.userapi.utils.JedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户信息controller
 *
 * @author chenhonghao
 * @date 2019-07-17 17:22
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api("用户接口服务")
public class UserController {
    private static final String DICTIONARY="dictionart_";
    @Resource
    private MessageSender messageSender;
    @Resource
    private AsyncTask asyncTask;
    @Resource
    private JedisOperator jedisOperator;
    @Resource
    private WaybillBcListFacade waybillBcListFacade;

    @Auth
    @PostMapping("/create")
    @ApiOperation(value = "创建用户",notes = "创建用户")
    BaseResponse<Boolean> createUser(@RequestBody String data) {
        waybillBcListFacade.createUser();
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
        return BaseResponse.success();
    }

    @PostMapping("/create1")
    BaseResponse<String> getUser(@RequestBody String data) {
        return null;
    }
}
