package com.honghao.cloud.userapi.domain.mapper.master;

import com.honghao.cloud.userapi.domain.entity.WaybillBcListDetailAction;

/**
 * @author CHH
 */
public interface WaybillBcListDetailActionMapper {
    int deleteByPrimaryKey(String wId);

    int insert(WaybillBcListDetailAction record);

    int insertSelective(WaybillBcListDetailAction record);

    WaybillBcListDetailAction selectByPrimaryKey(String wId);

    int updateByPrimaryKeySelective(WaybillBcListDetailAction record);

    int updateByPrimaryKey(WaybillBcListDetailAction record);
}