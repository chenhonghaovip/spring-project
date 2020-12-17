package com.honghao.cloud.basic.common.utils;

import com.honghao.cloud.basic.common.base.BaseResponse;
import org.springframework.util.Assert;

/**
 * @author chenhonghao
 * @date 2020-06-24 15:29
 */
public class AssertUtils {
    public static void notNullAndIsTrue(BaseResponse baseResponse) {
        Assert.notNull(baseResponse, "结果不能为空");
        Assert.isTrue(baseResponse.isResult(), baseResponse.getRemark());
    }
}
