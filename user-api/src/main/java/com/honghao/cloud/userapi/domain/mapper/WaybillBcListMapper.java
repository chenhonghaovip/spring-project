package com.honghao.cloud.userapi.domain.mapper;

import com.honghao.cloud.userapi.domain.entity.WaybillBcList;

/**
 * @author CHH
 */
public interface WaybillBcListMapper {
    int deleteByPrimaryKey(String wId);

    int insert(WaybillBcList record);

    int insertSelective(WaybillBcList record);

    WaybillBcList selectByPrimaryKey(String wId);

    int updateByPrimaryKeySelective(WaybillBcList record);

    int updateByPrimaryKey(WaybillBcList record);
}