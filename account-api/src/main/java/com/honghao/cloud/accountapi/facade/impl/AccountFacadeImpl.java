package com.honghao.cloud.accountapi.facade.impl;


import com.honghao.cloud.accountapi.client.OrderClient;
import com.honghao.cloud.accountapi.domain.entity.Order;
import com.honghao.cloud.accountapi.domain.entity.WaybillBcList;
import com.honghao.cloud.accountapi.facade.AccountFacade;
import com.honghao.cloud.accountapi.service.AccountService;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.factory.ThreadPoolFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

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
    @Resource
    private OrderClient orderClient;


    @Override
    public BaseResponse createOrders(String data) {
        orderClient.create(data);
        orderService.createOrders(data);
        return BaseResponse.success();
    }

    @Override
    public List<WaybillBcList> batchQuery(List<String> list) {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Future<WaybillBcList> submit = threadPoolExecutor.submit(() -> {

            WaybillBcList waybillBcList = orderClient.queryWaybillBcList(list.get(0));
            countDownLatch.countDown();
            return waybillBcList;
        });

        Future<Order> submit1 = threadPoolExecutor.submit(() -> {
            Order order = orderClient.queryOrder(list.get(0));
            countDownLatch.countDown();
            return order;
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            WaybillBcList waybillBcList = submit.get();
            Order order = submit1.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
        }

        return list.stream().map(each-> WaybillBcList.builder().wId(each).batchId(each).build()).collect(Collectors.toList());
    }
}
