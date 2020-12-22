package com.honghao.cloud.userapi.service;

import com.honghao.cloud.userapi.domain.entity.CloudDeliveryMan;

/**
 * 订单处理
 *
 * @author chenhonghao
 * @date 2019-12-20 14:05
 */
public interface CloudOrderService {

    /**
     * 更新数据
     *
     * @param cloudDeliveryMan cloudDeliveryMan
     * @return int
     */
    int updateInfos(CloudDeliveryMan cloudDeliveryMan);

}
