package com.honghao.cloud.basic.common.base.netty;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenhonghao
 * @date 2020-10-22 11:41
 */
@Configuration
@ConditionalOnProperty(prefix = "enable", name = "message", havingValue = "true")
@EnableConfigurationProperties(NettyServerProperties.class)
public class NettyClientStarted {
    public NettyClientStarted(NettyServerProperties nettyServerProperties) {
        NettyClient.getInstance().connect(nettyServerProperties);
    }
}
