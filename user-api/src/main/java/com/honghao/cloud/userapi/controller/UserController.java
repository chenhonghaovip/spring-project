package com.honghao.cloud.userapi.controller;

import com.honghao.cloud.userapi.aspect.Auth;
import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.interceptor.UserInfoHolder;
import com.honghao.cloud.userapi.listener.rabbitmq.producer.MessageSender;
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
public class UserController {
    @Resource
    private MessageSender messageSender;

    @Auth
    @PostMapping("/create")
    BaseResponse<Boolean> createUser(@RequestBody String data) {
        log.info("ppppppppppp");
        String agentNo = UserInfoHolder.getOperator().getAgentNo();
        log.info("parameter：{}",agentNo);
        messageSender.pushInfoToUser("");
        return BaseResponse.success();
    }

    @PostMapping("/create1")
    BaseResponse<String> getUser(@RequestBody String data) {
        return null;
    }
}
