package com.honghao.cloud.userapi.domain.mapper;

import com.honghao.cloud.userapi.domain.entity.CloudDocument;

public interface CloudDocumentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CloudDocument record);

    int insertSelective(CloudDocument record);

    CloudDocument selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CloudDocument record);

    int updateByPrimaryKey(CloudDocument record);
}