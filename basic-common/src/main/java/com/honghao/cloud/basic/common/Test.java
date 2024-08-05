package com.honghao.cloud.basic.common;

import com.honghao.cloud.basic.common.exceptions.BusinessException;
import com.honghao.cloud.basic.common.exceptions.BusinessExceptionEnum;

/**
 * @author chenhonghao12
 * @version 1.0
 */
public class Test {


    public void test() {
        throw new BusinessException(BusinessExceptionEnum.NO_PERMISSION);
    }
}
