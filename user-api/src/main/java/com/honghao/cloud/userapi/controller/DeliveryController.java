package com.honghao.cloud.userapi.controller;

import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import com.honghao.cloud.userapi.dto.request.ReceiveDTO;
import com.honghao.cloud.userapi.test.designpatterns.tactics.ContextDeal;
import com.honghao.cloud.userapi.test.designpatterns.tactics.DeliveryImpl;
import com.honghao.cloud.userapi.test.designpatterns.tactics.SameCityImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 骑士操作控制
 *
 * @author chenhonghao
 * @date 2020-02-23 15:32
 */
@RestController("/deliveryController")
public class DeliveryController {
    @Resource
    private ContextDeal contextDeal;
    @Resource
    private SameCityImpl sameCityImpl;
    @Resource
    private DeliveryImpl deliveryImpl;

    @GetMapping("/listInfos")
    public BaseResponse<WaybillBcList> listInfos(@RequestParam("wId") String wId){
        setContextDeal(1);
        contextDeal.listInfos(wId);
        return BaseResponse.successData(new WaybillBcList());
    }

    @PostMapping("/pickupList")
    public BaseResponse pickupList(@RequestBody ReceiveDTO data){
        setContextDeal(data.getAbnormalType());
        return contextDeal.receiveShop(data);
    }

    private void setContextDeal(Integer type){
        if (Integer.valueOf(1).equals(type)){
            contextDeal.setDeliveryAction(sameCityImpl);
        }else {
            contextDeal.setDeliveryAction(deliveryImpl);
        }
    }
}
