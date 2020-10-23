package com.honghao.cloud.message.netty;

import com.honghao.cloud.basic.common.base.factory.ThreadPoolFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chenhonghao
 * @date 2020-10-22 13:59
 */
class NettyServer {
    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(1,1,"netty-server-init");
    /**
     * 初始化配置信息
     */
    void init(){
        threadPoolExecutor.submit(()->{
            ServerBootstrap bootstrap = new ServerBootstrap();
            EventLoopGroup eventExecutors = new NioEventLoopGroup();
            EventLoopGroup eventExecutors1 = new NioEventLoopGroup();
            try {

                bootstrap.group(eventExecutors,eventExecutors1)
                        .channel(NioServerSocketChannel.class)
                        .localAddress(8899)
                        .option(ChannelOption.SO_KEEPALIVE,true)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) {
                                socketChannel.pipeline().addLast(new NettyServerHandle());
                            }
                        });
                ChannelFuture sync = bootstrap.bind().sync();
                System.out.println("在8899上开启监听程序");
                sync.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    eventExecutors.shutdownGracefully().sync();
                    eventExecutors1.shutdownGracefully().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
