package com.honghao.cloud.userapi.domain.mapper.master;

import com.honghao.cloud.userapi.domain.entity.WaybillOrderRealInfo;

public interface WaybillOrderRealInfoMapper {
    int deleteByPrimaryKey(String wId);

    int insert(WaybillOrderRealInfo record);

    int insertSelective(WaybillOrderRealInfo record);

    WaybillOrderRealInfo selectByPrimaryKey(String wId);

    int updateByPrimaryKeySelective(WaybillOrderRealInfo record);

    int updateByPrimaryKey(WaybillOrderRealInfo record);
}