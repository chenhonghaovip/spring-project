package com.honghao.cloud.userapi.component;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.userapi.client.OrderClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * feign公用
 *
 * @author chenhonghao
 * @date 2020-05-27 14:59
 */
@Component
public class FeignCommon {
    @Resource
    private OrderClient orderClient;


    public <T> void test(T waybillBcList){
        orderClient.createUser(JSON.toJSONString(waybillBcList));
    }
}
