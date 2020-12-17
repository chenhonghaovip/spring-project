package com.honghao.cloud.orderapi.domain.mapper;

import com.honghao.cloud.orderapi.domain.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author CHH
 */
public interface OrderMapper {

    /**
     * 选择性插入
     *
     * @param record record
     * @return int
     */
    int insertSelective(Order record);

    /**
     * 主键查询
     *
     * @param wId wId
     * @return int
     */
    Order selectByPrimaryKey(String wId);

    /**
     * 选择性更新
     *
     * @param record record
     * @return int
     */
    int updateByPrimaryKeySelective(Order record);

    /**
     * 通过订单id批次查询订单是否存在
     *
     * @param list list
     * @return List<String>
     */
    List<String> batchQuery(@Param("list") List<String> list);


    /**
     * 批次插入信息
     *
     * @param list list
     * @return List<String>
     */
    int batchInsert(@Param("list") List<Order> list);

    /**
     * 乐观更新操作
     *
     * @param wId list
     * @return List<String>
     */
    int update(@Param("wId") String wId, @Param("version") int version);
}