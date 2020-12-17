package com.honghao.cloud.message.netty;

import com.honghao.cloud.basic.common.base.BaseDict;
import com.honghao.cloud.basic.common.factory.ThreadPoolFactory;
import com.honghao.cloud.basic.common.netty.DelimiterBasedFrameEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chenhonghao
 * @date 2020-10-22 13:59
 */
class NettyServer {
    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(1, 1, "netty-server-init");

    /**
     * 初始化配置信息
     */
    void init() {
        threadPoolExecutor.submit(() -> {
            ServerBootstrap bootstrap = new ServerBootstrap();
            EventLoopGroup eventExecutors = new NioEventLoopGroup();
            EventLoopGroup eventExecutors1 = new NioEventLoopGroup(4);
            try {

                bootstrap.group(eventExecutors, eventExecutors1)
                        .channel(NioServerSocketChannel.class)
                        .localAddress(8899)
                        // 保持连接
                        .option(ChannelOption.SO_KEEPALIVE, true)
                        // 有数据立即发送
                        .option(ChannelOption.TCP_NODELAY, true)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) {
                                ByteBuf delimiter = Unpooled.wrappedBuffer(BaseDict.DELIMITER.getBytes());
                                socketChannel.pipeline()
                                        /*
                                         * 将delimiter设置到DelimiterBasedFrameDecoder中，经过该解码一器进行处理之后，源数据将会
                                         * 被按照$(* *)$进行分隔，这里 MAX_LENGTH 指的是分隔的最大长度，即当读取到 MAX_LENGTH 个字节的数据之后，
                                         * 若还是未读取到分隔符，则舍弃当前数据段，因为其很有可能是由于码流紊乱造成的
                                         */
                                        .addLast(new DelimiterBasedFrameDecoder(BaseDict.MAX_LENGTH, delimiter))
//                                        .addLast(new StringDecoder())
                                        .addLast(new DelimiterBasedFrameEncoder(BaseDict.DELIMITER))
                                        .addLast(new NettyServerHandle());
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
