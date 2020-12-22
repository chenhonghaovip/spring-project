package com.honghao.cloud.orderapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.basic.common.base.BaseAssert;
import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.basic.common.base.RetryException;
import com.honghao.cloud.orderapi.aspect.TryAgain;
import com.honghao.cloud.orderapi.common.enums.ErrorCodeEnum;
import com.honghao.cloud.orderapi.domain.entity.Order;
import com.honghao.cloud.orderapi.domain.mapper.OrderMapper;
import com.honghao.cloud.orderapi.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

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
    public BaseResponse createBatchOrders(List<Order> data) {
        orderMapper.batchInsert(data);
        return BaseResponse.success();
    }

    @Override
    public List<String> batchQuery(List<String> wIds) {
        return orderMapper.batchQuery(wIds);
    }


    @TryAgain
    @Override
    public BaseResponse update(String wId) {
        Order order = orderMapper.selectByPrimaryKey(wId);
        BaseAssert.notNull(order, ErrorCodeEnum.PARAM_ERROR);
        System.out.println(JSON.toJSONString(order));
        if (Objects.equals(order.getOrderStatus(), 1) && orderMapper.update(wId, order.getVersion()) == 0) {
            throw new RetryException("乐观锁更新失败");
        }
        return BaseResponse.success();
    }

}
