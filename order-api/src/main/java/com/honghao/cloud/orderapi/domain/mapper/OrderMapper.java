package com.honghao.cloud.orderapi.domain.mapper;

import com.honghao.cloud.orderapi.domain.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(String wId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(String wId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    /**
     * 通过订单id批次查询订单是否存在
     * @param list list
     * @return List<String>
     */
    List<String> batchQuery(@Param("list") List<String> list);
}