package com.honghao.cloud.userapi.tactics;

import com.alibaba.fastjson.JSONObject;
import com.honghao.cloud.userapi.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author chenhonghao
 * @date 2020-03-11 14:17
 */
@Slf4j
@Service
public class SameCityService implements DeliveryService{
    private static JSONObject jsonObject;
    static {
        jsonObject = new JSONObject();
        jsonObject.put("time", LocalDateTime.now());
    }
    @Override
    public BaseResponse grabSingle(String data) {
        return null;
    }

    @Override
    public BaseResponse receiveShop(String data) {
        System.out.println("同城商家"+jsonObject.toJSONString());
        return null;
    }

    @Override
    public BaseResponse takeOrder(String data) {
        return null;
    }

    @Override
    public BaseResponse serviceOrder(String data) {
        return null;
    }

    @Override
    public BaseResponse deliveryManCancel(String data) {
        return null;
    }

    @Override
    public BaseResponse deliveryManException(String data) {
        return null;
    }
}
