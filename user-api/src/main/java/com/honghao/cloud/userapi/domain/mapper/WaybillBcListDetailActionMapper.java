package com.honghao.cloud.userapi.domain.mapper;

import com.honghao.cloud.userapi.domain.entity.WaybillBcListDetailAction;

public interface WaybillBcListDetailActionMapper {
    int deleteByPrimaryKey(String wId);

    int insert(WaybillBcListDetailAction record);

    int insertSelective(WaybillBcListDetailAction record);

    WaybillBcListDetailAction selectByPrimaryKey(String wId);

    int updateByPrimaryKeySelective(WaybillBcListDetailAction record);

    int updateByPrimaryKey(WaybillBcListDetailAction record);
}