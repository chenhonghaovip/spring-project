package com.honghao.cloud.userapi.test.designpatterns.tactics;

import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import com.honghao.cloud.userapi.dto.request.ReceiveDTO;
import org.springframework.stereotype.Component;

/**
 * 环境类
 *
 * @author chenhonghao
 * @date 2020-02-23 15:25
 */
@Component
public class ContextDeal {

    private DeliveryAction deliveryAction;

    public void setDeliveryAction(DeliveryAction deliveryAction) {
        this.deliveryAction = deliveryAction;
    }
    public BaseResponse<WaybillBcList> listInfos(String wId){
        return deliveryAction.listInfos(wId);
    }

    public BaseResponse receiveShop(ReceiveDTO request){
        return deliveryAction.receiveShop(request);
    }


}
