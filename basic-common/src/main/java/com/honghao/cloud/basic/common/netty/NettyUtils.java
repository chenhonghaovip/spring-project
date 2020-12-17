package com.honghao.cloud.basic.common.netty;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.basic.common.dto.MessageFuture;
import com.honghao.cloud.basic.common.dto.RpcMessage;
import com.honghao.cloud.basic.common.utils.PositiveAtomicCounter;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author chenhonghao
 * @date 2020-10-22 14:20
 */
@Slf4j
public class NettyUtils {
    /**
     * 用于异步获取返回结果
     */
    static final ConcurrentHashMap<Integer, MessageFuture> MAP = new ConcurrentHashMap<>();
    private final static ConcurrentMap<String, Channel> CHANNELS = new ConcurrentHashMap<>();
    private final static ConcurrentMap<String, Object> CHANNEL_LOCKS = new ConcurrentHashMap<>();
    static Bootstrap bootstrap;
    /**
     * 用于生成id,获取返回结果时通过此参数绑定请求结果与返回结果
     */
    private static PositiveAtomicCounter positiveAtomicCounter = new PositiveAtomicCounter();
    private static ChannelFuture connect;

    static void setConnect(ChannelFuture connect) {
        NettyUtils.connect = connect;
    }

    private static boolean channelIsOk(Channel channel) {
        return channel != null && channel.isActive();
    }

    static void reconnect(List<String> availList) {
        if (CollectionUtils.isEmpty(availList)) {
            return;
        }
        for (String serverAddress : availList) {
            try {
                acquireChannel(serverAddress);
            } catch (Exception e) {
                log.error("can not connect to {} cause:{}", serverAddress, e.getMessage(), e);
            }
        }
    }

    static Channel acquireChannel(String serverAddress) {
        Channel channelToServer = CHANNELS.get(serverAddress);
        if (channelIsOk(channelToServer)) {
            return channelToServer;
        }
        // 加锁操作
        CHANNEL_LOCKS.putIfAbsent(serverAddress, new Object());
        synchronized (CHANNEL_LOCKS.get(serverAddress)) {
            return doConnect(serverAddress);
        }
    }

    private static Channel doConnect(String serverAddress) {
        Channel channelToServer = CHANNELS.get(serverAddress);
        if (channelIsOk(channelToServer)) {
            return channelToServer;
        }
        Channel channelFromPool = null;
        try {
            String[] split = serverAddress.split(":");
            ChannelFuture connect = bootstrap.connect(split[0], Integer.valueOf(split[1]));

            try {
                connect.await(2, TimeUnit.SECONDS);
                if (connect.isCancelled()) {
                    throw new RuntimeException("connect cancelled, can not connect to services-server.", connect.cause());
                } else if (!connect.isSuccess()) {
                    throw new RuntimeException("connect failed, can not connect to services-server.", connect.cause());
                } else {
                    channelFromPool = connect.channel();
                    CHANNELS.put(serverAddress, channelFromPool);
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
            CHANNELS.put(serverAddress, channelFromPool);
        } catch (Exception exx) {
            log.error("{} register RM failed.", serverAddress);
        }
        return channelFromPool;
    }


    static void releaseChannel(Channel channel, String serverAddress) {
        if (channel == null || serverAddress == null) {
            return;
        }
        synchronized (CHANNEL_LOCKS.get(serverAddress)) {
            Channel channelToServer = CHANNELS.get(serverAddress);
            if (channel.compareTo(channelToServer) == 0) {
                destroyChannel(serverAddress, channel);
            }
        }
    }

    private static void destroyChannel(String serverAddress, Channel channel) {
        if (null == channel) {
            return;
        }
        try {
            if (channel.equals(CHANNELS.get(serverAddress))) {
                CHANNELS.remove(serverAddress);
                channel.close();
            }
        } catch (Exception exx) {
            log.error(exx.getMessage());
        }
    }

    /**
     * To string address string.
     *
     * @param address the address
     * @return the string
     */
    static String toStringAddress(SocketAddress address) {
        if (null == address) {
            return StringUtils.EMPTY;
        }
        return toStringAddress((InetSocketAddress) address);
    }

    /**
     * To string address string.
     *
     * @param address the address
     * @return the string
     */
    private static String toStringAddress(InetSocketAddress address) {
        return address.getAddress().getHostAddress() + ":" + address.getPort();
    }


    public static BaseResponse sendMessageLoad(Object object, int type) throws TimeoutException {
        List<String> list = new ArrayList<>(CHANNELS.keySet());
        int size = list.size();
        int i = 0;
        if (size <= 0) {
            return BaseResponse.error();
        } else if (size > 1) {
            Random random = new Random();
            i = random.nextInt(size - 1);
        }

        Channel channel = CHANNELS.get(list.get(i));
        return sendMessage(object, type, channel);
    }

    public static BaseResponse sendMessage(Object object, int type) throws TimeoutException {
        if (connect != null && connect.channel().isActive()) {
            Channel channel = connect.channel();
            return sendMessage(object, type, channel);
        } else {
            return null;
        }
    }

    private static BaseResponse sendMessage(Object object, int type, Channel channel) throws TimeoutException {
        if (channel != null && channel.isActive()) {
            int nextId = positiveAtomicCounter.getAndIncrement();
            RpcMessage rpcMessage = RpcMessage.builder().id(nextId).messageType(type).body(object).build();

            MessageFuture messageFuture = new MessageFuture();
            MAP.putIfAbsent(nextId, messageFuture);
            ChannelFuture channelFuture = channel.writeAndFlush(Unpooled.copiedBuffer(JSON.toJSONString(rpcMessage), CharsetUtil.UTF_8));
            channelFuture.addListener((ChannelFutureListener) future -> {
                if (!future.isSuccess()) {
                    MessageFuture messageFuture1 = MAP.remove(rpcMessage.getId());
                    if (messageFuture1 != null) {
                        messageFuture1.setResultMessage(future.cause());
                    }
                    channel.close();
                }
            });
            try {
                Object o = messageFuture.get(10000, TimeUnit.MILLISECONDS);
                if (o instanceof RpcMessage) {
                    RpcMessage result = (RpcMessage) o;
                    return JSON.parseObject(JSON.toJSONString(result.getBody()), BaseResponse.class);
                }
                return null;
            } catch (Exception e) {
                log.error("wait response error:{},ip:{},request:{}", e.getMessage(), "", JSON.toJSONString(object));
                if (e instanceof TimeoutException) {
                    throw (TimeoutException) e;
                } else {
                    throw new RuntimeException(e);
                }
            }
        } else {
            return null;
        }
    }
}
