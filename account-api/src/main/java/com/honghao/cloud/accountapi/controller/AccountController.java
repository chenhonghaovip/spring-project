package com.honghao.cloud.accountapi.controller;


import com.honghao.cloud.accountapi.facade.AccountFacade;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 用户信息controller
 *
 * @author chenhonghao
 * @date 2019-07-17 17:22
 */
@Slf4j
@RestController
@RequestMapping("/order")
@Api(value = "用户接口服务",tags = "账户服务")
public class AccountController {
    @Resource
    private AccountFacade orderFacade;

    @PostMapping("/create")
    @ApiOperation(value = "创建订单",notes = "创建订单")
    public BaseResponse createUser(@RequestParam String data) {
//        return orderFacade.createOrders(data);
        return BaseResponse.successData(LocalDateTime.now());
    }

}
