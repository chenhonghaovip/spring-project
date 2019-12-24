package com.honghao.cloud.userapi.facade;

import com.honghao.cloud.userapi.base.BaseResponse;

/**
 * 订单信息操作
 *
 * @author chenhonghao
 * @date 2019-07-18 17:27
 */
public interface WaybillBcListFacade {
    /**
     * 创建用户
     * @param data data
     * @return Boolean
     */
    Boolean createUser(String data);
    /**
     * 创建用户
     * @param data data
     * @return Boolean
     */
    Boolean createUser1(String data);
    /**
     * 创建用户
     * @param data data
     * @return Boolean
     */
    Boolean createUser2(String data);

    BaseResponse easypoi();

    BaseResponse test004();

    /**
     * 多数据源测试
     * @return BaseResponse
     */
    BaseResponse dateSource();

    /**
     * 反射测试
     * @return BaseResponse
     */
    BaseResponse reflexTest();
}
