package com.honghao.cloud.userapi.domain.mapper.master;

import com.honghao.cloud.userapi.domain.entity.WaybillBcListExtend;

public interface WaybillBcListExtendMapper {
    int deleteByPrimaryKey(String wId);

    int insert(WaybillBcListExtend record);

    int insertSelective(WaybillBcListExtend record);

    WaybillBcListExtend selectByPrimaryKey(String wId);

    int updateByPrimaryKeySelective(WaybillBcListExtend record);

    int updateByPrimaryKey(WaybillBcListExtend record);
}