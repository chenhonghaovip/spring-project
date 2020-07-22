package com.honghao.cloud.accountapi.client.hystrix;

import com.honghao.cloud.accountapi.client.OrderClient;
import com.honghao.cloud.accountapi.domain.entity.Order;
import com.honghao.cloud.accountapi.domain.entity.WaybillBcList;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 熔断降级配置
 *
 * @author chenhonghao
 * @date 2019-07-31 13:24
 */
@Slf4j
@Component
public class OrderFallbackFactory implements FallbackFactory<OrderClient> {
    @Override
    public OrderClient create(Throwable throwable) {
        return new OrderClient() {
            @Override
            public void create(String param) {
                log.info("account 服务降级");
            }

            @Override
            public WaybillBcList queryWaybillBcList(String param) {
                return new WaybillBcList();
            }

            @Override
            public Order queryOrder(String param) {
                return new Order();
            }
        };
    }
}
