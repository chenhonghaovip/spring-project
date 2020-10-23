package com.honghao.cloud.basic.common.base.netty;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chenhonghao
 * @date 2020-10-23 16:13
 */
@ConfigurationProperties(prefix = "message.server")
public class NettyServerProperties {
    private int port = 8899;

    private String[] ips = {"127.0.0.1"};

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String[] getIps() {
        return ips;
    }

    public void setIps(String[] ips) {
        this.ips = ips;
    }
}
