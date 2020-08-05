package com.honghao.cloud.accountapi.domain.mapper;

import com.honghao.cloud.accountapi.domain.entity.WaybillBcList;

public interface WaybillBcListMapper {
    int deleteByPrimaryKey(WaybillBcList key);

    int insert(WaybillBcList record);

    int insertSelective(WaybillBcList record);

    WaybillBcList selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(WaybillBcList record);

    int updateByPrimaryKey(WaybillBcList record);
}