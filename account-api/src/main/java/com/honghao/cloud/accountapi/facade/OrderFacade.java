package com.honghao.cloud.accountapi.facade;


import com.honghao.cloud.accountapi.base.BaseResponse;
import com.honghao.cloud.accountapi.domain.entity.WaybillBcList;

import java.util.List;

/**
 * 订单信息操作
 *
 * @author chenhonghao
 * @date 2019-07-18 17:27
 */
public interface OrderFacade {

    /**
     * 创建订单
     * @param data data
     * @return Boolean
     */
    BaseResponse createOrders(String data);

    /**
     * 批次查询
     * @param list list
     * @return List<WaybillBcList>
     */
    List<WaybillBcList> batchQuery(List<String> list);
}