package com.honghao.cloud.accountapi.service;

import com.honghao.cloud.accountapi.domain.entity.ShopInfo;
import com.honghao.cloud.basic.common.base.base.BaseResponse;

/**
 * @author chenhonghao
 * @date 2020-07-30 17:36
 */
public interface RabbitService {

    BaseResponse saveShop(ShopInfo shopInfo);
}
