package com.honghao.cloud.userapi.domain.mapper;

import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WaybillBcListMapper {
    int deleteByPrimaryKey(String wId);

    int insert(WaybillBcList record);

    int insertSelective(WaybillBcList record);

    WaybillBcList selectByPrimaryKey(String wId);

    int updateByPrimaryKeySelective(WaybillBcList record);

    int updateByPrimaryKey(WaybillBcList record);

    List<WaybillBcList> selectAllOrder(@Param("date") String date,@Param("orderType") Integer orderType);

    /**
     * 批量插入
     * @param list list
     * @return int
     */
    int insertBatch(List<WaybillBcList> list);
}