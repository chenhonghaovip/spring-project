package com.honghao.cloud.accountapi.facade.impl;


import com.honghao.cloud.accountapi.client.OrderClient;
import com.honghao.cloud.accountapi.domain.entity.Order;
import com.honghao.cloud.accountapi.domain.entity.WaybillBcList;
import com.honghao.cloud.accountapi.facade.OrderFacade;
import com.honghao.cloud.accountapi.service.OrderService;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.factory.ThreadPoolFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 订单操作实现类
 *
 * @author chenhonghao
 * @date 2019-07-18 17:28
 */
@Slf4j
@Service
public class OrderFacadeImpl implements OrderFacade {
    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(10,40,"test");
    @Resource
    private OrderService orderService;
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

    private static FutureTask<Integer> futureTask = new FutureTask<>(() -> {
        Thread.sleep(100000);
        return new Random(100).nextInt(100);
    });

    public static void main(String[] args) {
        Future<Integer> submit = threadPoolExecutor.submit(() -> {
            Thread.sleep(10000);
            return new Random(100).nextInt();
        });

        for (int i = 0; i < 4; i++) {
            threadPoolExecutor.submit(()->{
                try {
                    Integer integer = submit.get();
                    System.out.println(Thread.currentThread().getName()+integer);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
