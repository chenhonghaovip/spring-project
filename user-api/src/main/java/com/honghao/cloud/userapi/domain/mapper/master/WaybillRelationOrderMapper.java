package com.honghao.cloud.userapi.domain.mapper.master;

import com.honghao.cloud.userapi.domain.entity.WaybillRelationOrder;

public interface WaybillRelationOrderMapper {
    int deleteByPrimaryKey(String wId);

    int insert(WaybillRelationOrder record);

    int insertSelective(WaybillRelationOrder record);

    WaybillRelationOrder selectByPrimaryKey(String wId);

    int updateByPrimaryKeySelective(WaybillRelationOrder record);

    int updateByPrimaryKey(WaybillRelationOrder record);
}