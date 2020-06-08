package com.honghao.cloud.orderapi.facade.impl;

import com.honghao.cloud.orderapi.base.BaseResponse;
import com.honghao.cloud.orderapi.domain.entity.WaybillBcList;
import com.honghao.cloud.orderapi.facade.OrderFacade;
import com.honghao.cloud.orderapi.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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
    @Resource
    private OrderService orderService;


    @Override
    public BaseResponse createOrders(String data) {
        orderService.createOrders(data);
        return BaseResponse.success();
    }

    @Override
    public List<WaybillBcList> batchQuery(List<String> list) {
        log.info("batchQuery:{}",list);
        return list.stream().map(each-> WaybillBcList.builder().wId(each).batchId(each).build()).collect(Collectors.toList());
    }
}
