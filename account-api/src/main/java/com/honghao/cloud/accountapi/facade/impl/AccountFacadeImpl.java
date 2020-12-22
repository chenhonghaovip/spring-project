package com.honghao.cloud.accountapi.facade.impl;


import com.honghao.cloud.accountapi.facade.AccountFacade;
import com.honghao.cloud.accountapi.service.AccountService;
import com.honghao.cloud.basic.common.base.BaseResponse;
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
public class AccountFacadeImpl implements AccountFacade {
    @Resource
    private AccountService orderService;

    @Override
    public BaseResponse createOrders(String data) {
        orderService.createOrders(data);
        return BaseResponse.success();
    }

}
