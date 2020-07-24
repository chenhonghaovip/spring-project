package com.honghao.cloud.accountapi.controller;


import com.honghao.cloud.accountapi.domain.entity.WaybillBcList;
import com.honghao.cloud.accountapi.facade.AccountFacade;
import com.honghao.cloud.accountapi.service.AccountService;
import com.honghao.cloud.accountapi.service.RedisService;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
public class AccountController {
    @Resource
    private AccountFacade orderFacade;
    @Resource
    private AccountService accountService;
    @Resource
    private RedisService redisService;

    @PostMapping("/create")
    @ApiOperation(value = "创建订单",notes = "创建订单")
    public BaseResponse createUser(@RequestParam String data) {
        return orderFacade.createOrders(data);
    }


    /**
     * 批次查询
     * @param list list
     * @return List<WaybillBcList>
     */
    @PostMapping("/batchQuery")
    public List<WaybillBcList> batchQuery(@RequestBody List<String> list){
        return orderFacade.batchQuery(list);
    }

    /**
     * 批次查询
     * @param userId userId
     * @return List<WaybillBcList>
     */
    @GetMapping("/singleQuery")
    public BaseResponse singleQuery(@RequestParam("userId") String userId){
        return redisService.redisList(userId);
    }
}
