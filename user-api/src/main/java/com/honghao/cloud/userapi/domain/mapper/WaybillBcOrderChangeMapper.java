package com.honghao.cloud.userapi.domain.mapper;

import com.honghao.cloud.userapi.domain.entity.WaybillBcOrderChange;

public interface WaybillBcOrderChangeMapper {
    int deleteByPrimaryKey(String id);

    int insert(WaybillBcOrderChange record);

    int insertSelective(WaybillBcOrderChange record);

    WaybillBcOrderChange selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WaybillBcOrderChange record);

    int updateByPrimaryKey(WaybillBcOrderChange record);
}