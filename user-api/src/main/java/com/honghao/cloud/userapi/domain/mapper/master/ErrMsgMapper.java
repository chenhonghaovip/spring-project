package com.honghao.cloud.userapi.domain.mapper.master;

import com.honghao.cloud.userapi.domain.entity.ErrMsg;

import java.util.List;

public interface ErrMsgMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(ErrMsg record);

    ErrMsg selectByPrimaryKey(Long id);

    List<ErrMsg> select();
}