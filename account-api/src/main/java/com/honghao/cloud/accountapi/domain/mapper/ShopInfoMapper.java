package com.honghao.cloud.accountapi.domain.mapper;

import com.honghao.cloud.accountapi.domain.entity.ShopInfo;

import java.util.List;

public interface ShopInfoMapper {
    int deleteByPrimaryKey(String shopId);

    int insert(ShopInfo record);

    int insertSelective(ShopInfo record);

    ShopInfo selectByPrimaryKey(String shopId);

    int updateByPrimaryKeySelective(ShopInfo record);

    int updateByPrimaryKey(ShopInfo record);

    List<ShopInfo> batchQuery();
}