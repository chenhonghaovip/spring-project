package com.honghao.cloud.accountapi.controller;

import com.honghao.cloud.accountapi.domain.entity.ShopInfo;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.netty.NettyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.concurrent.TimeoutException;

/**
 * netty控制处理
 *
 * @author chenhonghao
 * @date 2020-10-22 14:48
 */
@RestController
@RequestMapping("/nettyController")
public class NettyController {

    @PostMapping("/netty")
    public BaseResponse netty(){
        ShopInfo shopInfo = ShopInfo.builder().shopUrl("shop").shopPrice(BigDecimal.TEN).shopName("pinguo").build();
        try {
            return NettyUtils.sendMessage(shopInfo);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
