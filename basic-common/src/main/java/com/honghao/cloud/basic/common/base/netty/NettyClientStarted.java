package com.honghao.cloud.basic.common.base.netty;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author chenhonghao
 * @date 2020-10-22 11:41
 */
@Configuration
public class NettyClientStarted {

    @PostConstruct
    public void init(){
        NettyClient.getInstance().connect("127.0.0.1",8899);
    }
}
