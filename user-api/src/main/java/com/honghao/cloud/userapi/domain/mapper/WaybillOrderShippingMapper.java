package com.honghao.cloud.userapi.domain.mapper;

import com.honghao.cloud.userapi.domain.entity.WaybillOrderShipping;

public interface WaybillOrderShippingMapper {
    int deleteByPrimaryKey(String wId);

    int insert(WaybillOrderShipping record);

    int insertSelective(WaybillOrderShipping record);

    WaybillOrderShipping selectByPrimaryKey(String wId);

    int updateByPrimaryKeySelective(WaybillOrderShipping record);

    int updateByPrimaryKey(WaybillOrderShipping record);
}