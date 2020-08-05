package com.honghao.cloud.accountapi.facade.impl;


import com.honghao.cloud.accountapi.facade.AccountFacade;
import com.honghao.cloud.accountapi.service.AccountService;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.factory.ThreadPoolFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 订单操作实现类
 *
 * @author chenhonghao
 * @date 2019-07-18 17:28
 */
@Slf4j
@Service
public class AccountFacadeImpl implements AccountFacade {
    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(10,40,"test");
    @Resource
    private AccountService orderService;

    @Override
    public BaseResponse createOrders(String data) {
        orderService.createOrders(data);
        return BaseResponse.success();
    }

}
