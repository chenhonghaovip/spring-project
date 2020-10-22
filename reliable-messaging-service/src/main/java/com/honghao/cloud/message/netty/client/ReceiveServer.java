package com.honghao.cloud.message.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chenhonghao
 * @date 2020-10-21 17:20
 */
@Component
public class ReceiveServer {
    @Resource
    private SendClientHandler sendClientHandler;
    // 和服务器建立连接
    public void connect(String host, int port) throws Exception {

        // 配置连接信息
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(sendClientHandler);
                }
            });
            // 建立连接
            ChannelFuture f = b.connect(host, port).sync();
            // 发送数据
            ByteBuf sendBuf = Unpooled.copiedBuffer("我是客户端001".getBytes());
            f.channel().writeAndFlush(sendBuf);
            // 阻塞直到连接被关闭时优雅的退出
            f.channel().closeFuture().sync();
        } finally {
            // 释放相关资源
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        ReceiveServer receiveServer = new ReceiveServer();
        try {
            receiveServer.connect("17.0.0.1",8899);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
