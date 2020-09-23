package com.honghao.cloud.accountapi.service.impl;

import com.honghao.cloud.accountapi.domain.entity.ShopInfo;
import com.honghao.cloud.accountapi.domain.mapper.ShopInfoMapper;
import com.honghao.cloud.accountapi.service.RabbitService;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author chenhonghao
 * @date 2020-07-30 17:37
 */
@Slf4j
@Service
public class RabbitServiceImpl implements RabbitService {
    @Resource
    private ShopInfoMapper shopInfoMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse saveShop(ShopInfo shopInfo) {
        shopInfoMapper.insert(shopInfo);
        return BaseResponse.success();
    }
}
