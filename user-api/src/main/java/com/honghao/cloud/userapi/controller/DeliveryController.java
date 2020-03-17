package com.honghao.cloud.userapi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.tactics.DeliveryService;
import com.honghao.cloud.userapi.tactics.TacticsEnum;
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
    private Map<String, DeliveryService> deliveryServiceMap;

    @PostMapping("/deliveryAction")
    public BaseResponse receiveShop(@RequestBody String data){
        JSONObject json = JSON.parseObject(data);
        Integer orderType = json.getInteger("orderType");
        if (TacticsEnum.CODES.contains(orderType)){
            DeliveryService deliveryService = deliveryServiceMap.get(TacticsEnum.formCode(orderType).getDeliveryServiceName());
            deliveryService.receiveShop(data);
            return BaseResponse.success();
        }
        return BaseResponse.error("订单类型不合法");
    }




    /**
     * 抢单操作
     * @param data data
     * @return Result
     */
    @PostMapping("/grabSingle")
    BaseResponse grabSingle(@RequestBody String data) {
        return BaseResponse.success();
    }

    /**
     * 确认操作
     * @param data data
     * @return Result
     */
    @PostMapping("/confirm")
    BaseResponse confirm(@RequestBody String data) {
        return BaseResponse.success();
    }

    /**
     * 到店操作
     * @param data data
     * @return Result
     */
    @PostMapping("/receiveShop")
    BaseResponse receiveShop1(@RequestBody String data) {
        return BaseResponse.success();
    }

    /**
     * 取单操作
     * @param data data
     * @return Result
     */
    @PostMapping("/takeOrder")
    BaseResponse takeOrder(@RequestBody String data) {
        return BaseResponse.success();
    }

    /**
     * 核价操作
     * @param data data
     * @return Result
     */
    @PostMapping("/nuclearPrice")
    BaseResponse nuclearPrice(@RequestBody String data) {
        return BaseResponse.success();
    }

    /**
     * 打单操作
     * @param data data
     * @return Result
     */
    @PostMapping("/makeSingle")
    BaseResponse makeSingle(@RequestBody String data) {
        return BaseResponse.success();
    }

    /**
     * 送达
     * @param data data
     * @return Result
     */
    @PostMapping("/serviceOrder")
    BaseResponse serviceOrder(@RequestBody String data) {
        return BaseResponse.success();
    }

    /**
     * 骑士取消订单
     * @param data data
     * @return Result
     */
    @PostMapping("/deliveryManCancel")
    BaseResponse deliveryManCancel(@RequestBody String data) {
        return BaseResponse.success();
    }


    /**
     * 骑士异常订单
     * @param data data
     * @return Result
     */
    @PostMapping("/deliveryManException")
    BaseResponse deliveryManException(@RequestBody String data) {
        return BaseResponse.success();
    }

}
