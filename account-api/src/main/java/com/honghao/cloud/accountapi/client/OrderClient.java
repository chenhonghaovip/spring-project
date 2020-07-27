package com.honghao.cloud.accountapi.client;

import com.honghao.cloud.accountapi.client.hystrix.OrderFallbackFactory;
import com.honghao.cloud.accountapi.domain.entity.Order;
import com.honghao.cloud.accountapi.domain.entity.WaybillBcList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 账户服务接口
 *
 * @author chenhonghao
 * @date 2019-07-31 13:19
 */
@FeignClient(name = OrderClient.SERVICE_ID,fallbackFactory = OrderFallbackFactory.class)
public interface OrderClient {
    String SERVICE_ID="order-api";

    /**
     * 账户服务调用
     * @param param string
     */
    @PostMapping("/create")
    void create(@RequestBody String param);

    /**
     * 账户服务调用
     * @param param string
     * @return order
     */
    @PostMapping("/create1")
    WaybillBcList queryWaybillBcList(@RequestBody String param);

    /**
     * 账户服务调用
     * @param param string
     * @return order
     */
    @PostMapping("/create2")
    Order queryOrder(@RequestBody String param);
}
