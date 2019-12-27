package com.honghao.cloud.userapi.domain.mapper.master;

import com.honghao.cloud.userapi.domain.entity.WaybillMerchantsArbitrament;

/**
 * @author CHH
 */
public interface WaybillMerchantsArbitramentMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(WaybillMerchantsArbitrament record);

    int insertSelective(WaybillMerchantsArbitrament record);

    WaybillMerchantsArbitrament selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(WaybillMerchantsArbitrament record);

    int updateByPrimaryKey(WaybillMerchantsArbitrament record);
}