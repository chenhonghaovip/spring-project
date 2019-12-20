package com.honghao.cloud.userapi.service.impl;

import com.honghao.cloud.userapi.domain.entity.CloudDeliveryMan;
import com.honghao.cloud.userapi.domain.mapper.slave.CloudOrderMapper;
import com.honghao.cloud.userapi.service.CloudOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 订单处理
 *
 * @author chenhonghao
 * @date 2019-12-20 14:05
 */
@Service
@Slf4j
public class CloudOrderServiceImpl implements CloudOrderService {
    @Resource
    private CloudOrderMapper cloudOrderMapper;

    @Override
    public int updateInfos(CloudDeliveryMan cloudDeliveryMan) {
        return cloudOrderMapper.updateByPrimaryKey(cloudDeliveryMan);
    }
}
