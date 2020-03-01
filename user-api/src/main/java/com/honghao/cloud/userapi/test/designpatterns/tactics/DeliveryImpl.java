package com.honghao.cloud.userapi.test.designpatterns.tactics;

import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 快递
 *
 * @author chenhonghao
 * @date 2020-02-23 20:05
 */
@Slf4j
@Service
public class DeliveryImpl implements DeliveryAction{
    @Override
    public <T> BaseResponse receiveShop(T t) {
        return null;
    }

    @Override
    public BaseResponse<WaybillBcList> listInfos(String wId) {
        log.info("DeliveryImpl");
        return null;
    }
}
