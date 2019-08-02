package com.honghao.cloud.userapi.domain.mapper;

import com.honghao.cloud.userapi.domain.entity.CloudDict;

public interface CloudDictMapper {
    int deleteByPrimaryKey(String id);

    int insert(CloudDict record);

    int insertSelective(CloudDict record);

    CloudDict selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CloudDict record);

    int updateByPrimaryKey(CloudDict record);
}