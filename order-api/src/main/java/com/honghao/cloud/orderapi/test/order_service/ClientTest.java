package com.honghao.cloud.orderapi.test.order_service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 场景类
 *
 * @author chenhonghao
 * @date 2020-03-13 10:06
 */
@Slf4j
@Component
public class ClientTest {
    @Resource
    private Context context;
    @Resource
    private Map<String,BaseOrderStatus> baseOrderStatusMap;

    @Test
    public void test(){
        Context context = new Context();
        context.setBaseOrderStatus(new OrdersStatus());
        context.toShop();
    }


    @Test
    public void test1(Integer orderStatus){
        if (OrderStatusMapping.CODES.contains(orderStatus)){
            BaseOrderStatus baseOrderStatus = baseOrderStatusMap.get(OrderStatusMapping.formCode(orderStatus).getDesc());
            context.setBaseOrderStatus(baseOrderStatus);
            context.toShop();
        }
    }

}
