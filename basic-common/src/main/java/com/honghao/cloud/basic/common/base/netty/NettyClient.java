package com.honghao.cloud.basic.common.base.netty;

import com.honghao.cloud.basic.common.base.factory.NamedThreadFactory;
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

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author chenhonghao
 * @date 2020-10-22 11:41
 */
@Slf4j
class NettyClient {
    protected final ScheduledExecutorService timerExecutor = new ScheduledThreadPoolExecutor(1,
            new NamedThreadFactory("timeoutChecker", 1, true));

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
                // 保持连接
                .option(ChannelOption.SO_KEEPALIVE,true)
                // 有数据立即发送
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                // 超时检测的handler,值为0时表示不启用
                                .addLast(new IdleStateHandler(0, 0, 30))
                                .addLast(new NettyClientHandle());
                    }
                });
        NettyUtils.bootstrap = bootstrap;
        return bootstrap;
    }

    /**
     * 初始化连接处理
     * @param nettyServerProperties nettyServerProperties
     */
    void connect(NettyServerProperties nettyServerProperties){
        int port = nettyServerProperties.getPort();
        List<String> addressList = Arrays.asList(nettyServerProperties.getIps()).stream().map(each->each+":"+port).collect(Collectors.toList());

        timerExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                NettyUtils.reconnect(addressList);
            }
        }, 60, 10, TimeUnit.MILLISECONDS);


//        ips.forEach(each->{
//            String address = each + ":" + port;
//            // 判断是否已经进行过初始化连接
//            if (NettyUtils.hasConnection(address)){
//                return;
//            }
//            ChannelFuture connect = this.bootstrap.connect(each, port);
//
//            try {
//                connect.await(2, TimeUnit.SECONDS);
//                if (connect.isCancelled()) {
//                    throw new RuntimeException("connect cancelled, can not connect to services-server.",connect.cause());
//                } else if (!connect.isSuccess()) {
//                    throw new RuntimeException("connect failed, can not connect to services-server.",connect.cause());
//                } else {
//                    Channel channel = connect.channel();
//                    NettyUtils.Server server = new NettyUtils.Server(address,channel);
//                    NettyUtils.CONNECTION_LIST.add(server);
//                }
//            } catch (InterruptedException e) {
//                log.error(e.getMessage());
//            }
//        });
    }

    void connect(String host, int port){
        // 连接到远程节点，等待连接完成
        try {
            ChannelFuture f = this.bootstrap.connect(host,port);
            try {
                f.await(2, TimeUnit.SECONDS);
                if (f.isCancelled()) {
                    throw new RuntimeException("connect cancelled, can not connect to services-server.",f.cause());
                } else if (!f.isSuccess()) {
                    throw new RuntimeException("connect failed, can not connect to services-server.",f.cause());
                } else {
                    NettyUtils.setConnect(f);
                }
            } catch (Exception e) {
                throw new RuntimeException( "can not connect to services-server.",e);
            }
            System.out.println("客户端启动");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
