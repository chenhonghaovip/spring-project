package com.honghao.cloud.userapi.facade;

import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;

/**
 * 请求合并
 *
 * @author chenhonghao
 * @date 2020-02-14 21:11
 */
public interface BatchFacade {

    /**
     * 请求合并测试
     * @param data data
     * @return BaseResponse
     * @throws Exception Exception
     */
    BaseResponse<WaybillBcList> queryCommon(String data) throws Exception;
}
