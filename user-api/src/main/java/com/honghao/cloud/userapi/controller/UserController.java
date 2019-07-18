package com.honghao.cloud.userapi.controller;

import com.honghao.cloud.userapi.aspect.Auth;
import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.interceptor.UserInfoHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Auth
    @PostMapping("/create")
    BaseResponse createUser(@RequestBody String data) {
        log.info("ppppppppppp");
        String agentNo = UserInfoHolder.getOperator().getAgentNo();
        log.info("parameter：{}",agentNo);
        return BaseResponse.success();
//        return BaseResponse.successData(data);
    }

    @PostMapping("/create1")
    BaseResponse<String> getUser(@RequestBody String data) {
        return null;
    }
}
