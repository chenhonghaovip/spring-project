package com.honghao.cloud.userapi.domain.mapper;

import com.honghao.cloud.userapi.domain.entity.CloudReasonConfig;

public interface CloudReasonConfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(CloudReasonConfig record);

    int insertSelective(CloudReasonConfig record);

    CloudReasonConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CloudReasonConfig record);

    int updateByPrimaryKey(CloudReasonConfig record);
}