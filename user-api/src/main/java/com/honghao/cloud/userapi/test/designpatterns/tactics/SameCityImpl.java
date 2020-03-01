package com.honghao.cloud.userapi.test.designpatterns.tactics;

import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import com.honghao.cloud.userapi.domain.mapper.master.WaybillBcListMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 骑士操作策略
 *
 * @author chenhonghao
 * @date 2020-02-23 15:23
 */
@Slf4j
@Service
public class SameCityImpl implements DeliveryAction{
    @Resource
    private WaybillBcListMapper waybillBcListMapper;

    @Override
    public <T> BaseResponse receiveShop(T t) {

        return null;
    }

    @Override
    public BaseResponse<WaybillBcList> listInfos(String wId) {
        log.info("SameCityImpl");
        return BaseResponse.successData(waybillBcListMapper.selectByPrimaryKey(wId));
    }
}
