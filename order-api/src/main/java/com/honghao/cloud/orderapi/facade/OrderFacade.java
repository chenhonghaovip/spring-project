package com.honghao.cloud.orderapi.facade;

import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.orderapi.dto.request.MsgDTO;

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
     * 创建批量订单
     * @param data data
     * @return Boolean
     */
    BaseResponse createBatchOrder(String data);


    /**
     * 批次查询
     * @param list list
     * @return List<WaybillBcList>
     */
    List<Long> batchQuery(List<MsgDTO> list);
}
