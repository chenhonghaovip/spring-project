package com.honghao.cloud.userapi.domain.mapper.master;

import com.honghao.cloud.userapi.domain.entity.WaybillOrderShipping;

/**
 * @author CHH
 */
public interface WaybillOrderShippingMapper {
    int deleteByPrimaryKey(String wId);

    int insert(WaybillOrderShipping record);

    int insertSelective(WaybillOrderShipping record);

    WaybillOrderShipping selectByPrimaryKey(String wId);

    int updateByPrimaryKeySelective(WaybillOrderShipping record);

    int updateByPrimaryKey(WaybillOrderShipping record);
}