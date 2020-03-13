package com.honghao.cloud.orderapi.controller;

import com.honghao.cloud.orderapi.base.BaseResponse;
import com.honghao.cloud.orderapi.config.TestThreadPoolConfig;
import com.honghao.cloud.orderapi.domain.entity.WaybillBcList;
import com.honghao.cloud.orderapi.test.order_service.BaseOrderStatus;
import com.honghao.cloud.orderapi.test.order_service.Context;
import com.honghao.cloud.orderapi.test.order_service.OrderStatusMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 测试
 *
 * @author chenhonghao
 * @date 2019-11-16 17:01
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private Context context;
    @Resource
    private Map<String, BaseOrderStatus> baseOrderStatusMap;

    @PostMapping("/test01")
    public BaseResponse<String> test01(@RequestBody String data){
        ThreadPoolExecutor threadPoolExecutor = TestThreadPoolConfig.create(1,5);
        threadPoolExecutor.execute(() -> System.out.println("111111111"));

        return BaseResponse.successData("123456");
    }

    /**
     * 状态模式测试
     * @param data data
     * @return BaseResponse<String>
     */
    @PostMapping("/test02")
    public BaseResponse<String> test02(@RequestBody WaybillBcList data){
        if (OrderStatusMapping.CODES.contains(data.getOrderStatus())){
            BaseOrderStatus baseOrderStatus = baseOrderStatusMap.get(OrderStatusMapping.formCode(data.getOrderStatus()).getDesc());
            context.setBaseOrderStatus(baseOrderStatus);
            context.toShop();
        }
        return BaseResponse.successData("123456");
    }
}
