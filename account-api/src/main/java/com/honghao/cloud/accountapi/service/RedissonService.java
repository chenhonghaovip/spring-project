package com.honghao.cloud.accountapi.service;

import com.honghao.cloud.basic.common.base.BaseResponse;

/**
 * redisson服务
 *
 * @author chenhonghao
 * @date 2020-12-21 15:33
 */
public interface RedissonService {

    /**
     * redisson加锁操作
     *
     * @param userId userId
     * @return BaseResponse
     */
    BaseResponse redissonLock(String userId);


}
