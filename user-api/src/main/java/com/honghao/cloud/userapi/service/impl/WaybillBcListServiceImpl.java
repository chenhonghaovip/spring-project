package com.honghao.cloud.userapi.service.impl;

import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import com.honghao.cloud.userapi.domain.mapper.WaybillBcListMapper;
import com.honghao.cloud.userapi.service.WaybillBcListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 订单服务实现类
 *
 * @author chenhonghao
 * @date 2019-07-18 17:32
 */
@Service
public class WaybillBcListServiceImpl implements WaybillBcListService {
    @Resource
    private WaybillBcListMapper waybillBcListMapper;

    @Override
    public void createUser(WaybillBcList waybillBcList) {
        waybillBcListMapper.insert(waybillBcList);
    }
}
