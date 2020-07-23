package com.honghao.cloud.userapi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.common.enums.TacticsEnum;
import com.honghao.cloud.userapi.tactics.KnightService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 骑士操作控制
 *
 * @author chenhonghao
 * @date 2020-02-23 15:32
 */
@RestController("/deliveryController")
public class DeliveryController {
    @Resource
    private Map<String, KnightService> deliveryServiceMap;

    @PostMapping("/deliveryAction")
    public BaseResponse receiveShop(@RequestBody String data){
        JSONObject json = JSON.parseObject(data);
        Integer orderType = json.getInteger("orderType");
        if (TacticsEnum.CODES.contains(orderType)){
            KnightService deliveryService = deliveryServiceMap.get(TacticsEnum.formCode(orderType).getDeliveryServiceName());
            deliveryService.receiveShop(data);
            return BaseResponse.success();
        }
        return BaseResponse.error("订单类型不合法");
    }
}
