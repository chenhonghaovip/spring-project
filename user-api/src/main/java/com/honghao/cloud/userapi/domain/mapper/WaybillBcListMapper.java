package com.honghao.cloud.userapi.domain.mapper;

import com.honghao.cloud.userapi.domain.entity.WaybillBcList;

/**
 * @author CHH
 */
public interface WaybillBcListMapper {
    /**
     * 插入
     * @param record WaybillBcList
     * @return int
     */
    int insert(WaybillBcList record);
    /**
     * 插入
     * @param record WaybillBcList
     * @return int
     */
    int insertSelective(WaybillBcList record);
    /**
     * 插入
     * @param wId WaybillBcList
     * @return WaybillBcList
     */
    WaybillBcList selectByPrimaryKey(String wId);
    /**
     * 插入
     * @param record WaybillBcList
     * @return int
     */
    int updateByPrimaryKeySelective(WaybillBcList record);

}