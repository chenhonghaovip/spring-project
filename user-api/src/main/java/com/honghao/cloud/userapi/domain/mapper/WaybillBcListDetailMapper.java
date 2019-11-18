package com.honghao.cloud.userapi.domain.mapper;

import com.honghao.cloud.userapi.domain.entity.WaybillBcListDetail;

public interface WaybillBcListDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(WaybillBcListDetail record);

    int insertSelective(WaybillBcListDetail record);

    WaybillBcListDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WaybillBcListDetail record);

    int updateByPrimaryKey(WaybillBcListDetail record);
}