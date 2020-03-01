package com.honghao.cloud.userapi.test.designpatterns.tactics;

import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;

/**
 * 骑士操作
 *
 * @author chenhonghao
 * @date 2020-02-23 15:23
 */
public interface DeliveryAction {

    /**
     * 到店操作
     * @param t 请求参数
     * @return BaseResponse
     */
    <T> BaseResponse receiveShop(T t);

    BaseResponse<WaybillBcList> listInfos(String wId);
}
