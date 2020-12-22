package com.honghao.cloud.userapi.tactics;


import com.honghao.cloud.basic.common.base.BaseResponse;

/**
 * 骑士操作接口
 *
 * @author chenhonghao
 * @date 2020-03-11 14:15
 */
public interface KnightService {

    /**
     * 到店操作
     *
     * @param data data
     * @return Result
     */
    BaseResponse receiveShop(String data);
}
