package com.honghao.cloud.userapi.controller;

import com.honghao.cloud.userapi.base.BaseResponse;
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

    @PostMapping("/create")
    BaseResponse createUser(@RequestBody String data) {
        return BaseResponse.success();
    }

    @PostMapping("/create1")
    BaseResponse<String> getUser(@RequestBody String data) {
        return null;
    }
}
