package com.honghao.cloud.userapi.domain.mapper.master;

import com.honghao.cloud.userapi.domain.entity.WaybillBcListDetail;

import java.util.List;

/**
 * @author CHH
 */
public interface WaybillBcListDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(WaybillBcListDetail record);

    int insertSelective(WaybillBcListDetail record);

    WaybillBcListDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WaybillBcListDetail record);

    int updateByPrimaryKey(WaybillBcListDetail record);

    int insertBatch(List<WaybillBcListDetail> list);
}