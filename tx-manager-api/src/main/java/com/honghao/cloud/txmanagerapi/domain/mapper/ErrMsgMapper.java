package com.honghao.cloud.txmanagerapi.domain.mapper;

import com.honghao.cloud.txmanagerapi.domain.entity.ErrMsg;

public interface ErrMsgMapper {
    int insert(ErrMsg record);

    int insertSelective(ErrMsg record);
}