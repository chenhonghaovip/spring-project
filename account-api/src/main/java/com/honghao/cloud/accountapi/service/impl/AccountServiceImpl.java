package com.honghao.cloud.accountapi.service.impl;


import com.alibaba.fastjson.JSON;
import com.honghao.cloud.accountapi.domain.entity.WaybillBcList;
import com.honghao.cloud.accountapi.domain.mapper.WaybillBcListMapper;
import com.honghao.cloud.accountapi.service.AccountService;
import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.basic.common.bean.CacheTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 订单服务实现类
 *
 * @author chenhonghao
 * @date 2019-07-18 17:32
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    private WaybillBcListMapper waybillBcListMapper;
    @Resource
    private CacheTemplate<WaybillBcList> cacheTemplate;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrders(String data) {
        BaseResponse response = cacheTemplate.redisStringCache("order", data, () -> waybillBcListMapper.selectByPrimaryKey(data));
        if (response.isResult()){
            WaybillBcList order = (WaybillBcList) response.getData();
            System.out.println(JSON.toJSONString(order));
        }
    }
}
