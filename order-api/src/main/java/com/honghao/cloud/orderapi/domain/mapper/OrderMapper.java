package com.honghao.cloud.orderapi.domain.mapper;

import com.honghao.cloud.orderapi.domain.entity.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(String wId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(String wId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}