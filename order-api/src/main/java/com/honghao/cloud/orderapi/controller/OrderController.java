package com.honghao.cloud.orderapi.controller;

import com.honghao.cloud.orderapi.base.BaseResponse;
import com.honghao.cloud.orderapi.facade.OrderFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户信息controller
 *
 * @author chenhonghao
 * @date 2019-07-17 17:22
 */
@Slf4j
@RestController
@RequestMapping("/order")
@Api("用户接口服务")
public class OrderController {
    @Resource
    private OrderFacade orderFacade;

    @PostMapping("/create")
    @ApiOperation(value = "创建订单",notes = "创建订单")
    BaseResponse<String> createUser(@RequestParam String data) {
        orderFacade.createUser(data);
        return BaseResponse.successData("name111");
    }

    @PostMapping("/create1")
    BaseResponse<String> getUser(@RequestBody String data) {
        return null;
    }
}