package com.honghao.cloud.userapi.facade.impl;

import com.honghao.cloud.userapi.facade.WaybillBcListFacade;
import com.honghao.cloud.userapi.service.WaybillBcListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 订单操作实现类
 *
 * @author chenhonghao
 * @date 2019-07-18 17:28
 */
@Service
public class WaybillBcListFacadeImpl implements WaybillBcListFacade {
    @Resource
    private WaybillBcListService waybillBcListService;


    @Override
    public Boolean createUser() {
        return null;
    }
}
