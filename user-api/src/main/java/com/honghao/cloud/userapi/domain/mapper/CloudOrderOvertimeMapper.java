package com.honghao.cloud.userapi.domain.mapper;

import com.honghao.cloud.userapi.domain.entity.CloudOrderOvertime;

public interface CloudOrderOvertimeMapper {
    int deleteByPrimaryKey(String id);

    int insert(CloudOrderOvertime record);

    int insertSelective(CloudOrderOvertime record);

    CloudOrderOvertime selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CloudOrderOvertime record);

    int updateByPrimaryKey(CloudOrderOvertime record);
}