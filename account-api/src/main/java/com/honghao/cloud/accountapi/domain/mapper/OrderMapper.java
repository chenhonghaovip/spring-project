package com.honghao.cloud.accountapi.domain.mapper;


import com.honghao.cloud.accountapi.domain.entity.Order;

/**
 * @author CHH
 */
public interface OrderMapper {
    int deleteByPrimaryKey(String wId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(String wId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}