package com.honghao.cloud.basic.common.base.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author chenhonghao
 * @date 2020-10-22 11:41
 */
@Slf4j
class NettyClient {
    private Bootstrap bootstrap;
    private static final NettyClient NETTY_CLIENT = new NettyClient();

    private NettyClient() {
        bootstrap = init();
    }

    static NettyClient getInstance(){
        return NETTY_CLIENT;
    }

    /**
     * 初始化配置信息
     * @return Bootstrap
     */
    private Bootstrap init(){
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        bootstrap.group(eventExecutors)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE,true)
//                .option(ChannelOption.SO_BACKLOG, 1024)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                // 超时检测的handler,值为0时表示不启用
                                .addLast(new IdleStateHandler(0, 0, 30))
                                .addLast(new NettyClientHandle());
                    }
                });
        return bootstrap;
    }

    void connect(String host, int port){
        String address = host + ":" + host;
        if (NettyUtils.hasConnection(address)){
            return;
        }
        // 连接到远程节点，等待连接完成
        try {
//            ChannelFuture connect = bootstrap.connect(host, port);
            ChannelFuture f = this.bootstrap.connect(host,port);
            try {
                f.await(2, TimeUnit.SECONDS);
                if (f.isCancelled()) {
                    throw new RuntimeException("connect cancelled, can not connect to services-server.",f.cause());
                } else if (!f.isSuccess()) {
                    throw new RuntimeException("connect failed, can not connect to services-server.",f.cause());
                } else {
                    NettyUtils.setConnect(f);
//                    RpcMessage rpcMessage = RpcMessage.builder().id(1).messageType(ProtocolConstants.INSERT).build();
//                    f.channel().writeAndFlush(Unpooled.copiedBuffer(JSON.toJSONString(rpcMessage), CharsetUtil.UTF_8));
                }
            } catch (Exception e) {
                throw new RuntimeException( "can not connect to services-server.",e);
            }


//            sync = bootstrap.connect(host, port).sync();
//            NettyUtils.setConnect(sync);
            System.out.println("客户端启动");
            // 阻塞操作，closeFuture()开启了一个channel监控器，
//            sync.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
