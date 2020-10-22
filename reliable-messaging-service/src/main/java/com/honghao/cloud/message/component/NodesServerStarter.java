package com.honghao.cloud.message.component;

import com.honghao.cloud.message.netty.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author wuweifeng wrote on 2019-12-11
 * @version 1.0
 */
@Slf4j
@Component
public class NodesServerStarter {

    @PostConstruct
    public void start() {
        NettyServer nodesServer = new NettyServer();
        try {
            nodesServer.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
