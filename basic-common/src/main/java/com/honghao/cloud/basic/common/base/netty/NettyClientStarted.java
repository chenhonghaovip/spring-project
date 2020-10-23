package com.honghao.cloud.basic.common.base.netty;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author chenhonghao
 * @date 2020-10-22 11:41
 */
@Configuration
@ConditionalOnProperty(prefix = "enable", name = "message", havingValue = "true")
@EnableConfigurationProperties(NettyServerProperties.class)
public class NettyClientStarted {

    @PostConstruct
    public void init(NettyServerProperties nettyServerProperties){
        NettyClient.getInstance().connect(nettyServerProperties);
    }

    public static void main(String[] args) {
        NettyClient.getInstance().connect("127.0.0.1",8899);
    }
}
