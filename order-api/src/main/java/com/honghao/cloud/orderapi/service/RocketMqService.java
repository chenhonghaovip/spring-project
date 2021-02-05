package com.honghao.cloud.orderapi.service;

import com.honghao.cloud.basic.common.base.BaseResponse;

/**
 * @author chenhonghao
 * @date 2021-02-05 16:00
 */
public interface RocketMqService {

    BaseResponse sendMessage();
}
