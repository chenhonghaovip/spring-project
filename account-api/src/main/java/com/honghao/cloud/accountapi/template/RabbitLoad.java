package com.honghao.cloud.accountapi.template;

import com.honghao.cloud.basic.common.base.base.BaseResponse;

/**
 * @author chenhonghao
 * @date 2020-07-25 16:15
 */
@FunctionalInterface
public interface RabbitLoad {
   BaseResponse run();
}
