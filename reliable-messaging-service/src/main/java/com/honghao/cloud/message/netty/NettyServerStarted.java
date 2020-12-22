package com.honghao.cloud.message.netty;

import com.honghao.cloud.message.controller.MessageController;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author chenhonghao
 * @date 2020-10-22 13:59
 */
@Component
public class NettyServerStarted {
    @Resource
    private MessageController messageController;

    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.init();
    }

    @PostConstruct
    public void init() {
        NettyServer nettyServer = new NettyServer();
        nettyServer.init();
        NettyServerHandle.messageController = messageController;
    }
}
