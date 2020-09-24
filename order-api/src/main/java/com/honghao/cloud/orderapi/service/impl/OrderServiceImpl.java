package com.honghao.cloud.orderapi.service.impl;

import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.orderapi.domain.entity.Order;
import com.honghao.cloud.orderapi.domain.mapper.OrderMapper;
import com.honghao.cloud.orderapi.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单服务实现类
 *
 * @author chenhonghao
 * @date 2019-07-18 17:32
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Override
    public BaseResponse createOrders(Order data) {
        orderMapper.insertSelective(data);
        return BaseResponse.success();
    }

    @Override
    public List<String> batchQuery(List<String> wIds) {
        return orderMapper.batchQuery(wIds);
    }

}
