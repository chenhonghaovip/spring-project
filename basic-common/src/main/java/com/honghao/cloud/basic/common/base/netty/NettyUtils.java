package com.honghao.cloud.basic.common.base.netty;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.dto.MessageFuture;
import com.honghao.cloud.basic.common.base.dto.RpcMessage;
import com.honghao.cloud.basic.common.base.utils.PositiveAtomicCounter;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author chenhonghao
 * @date 2020-10-22 14:20
 */
@Slf4j
public class NettyUtils {
    public static Bootstrap bootstrap;

    private AtomicLong atomicLong;
    /**
     * 用于异步获取返回结果
     */
    public static final ConcurrentHashMap<Integer, MessageFuture> MAP = new ConcurrentHashMap<>();
    /**
     * 用于生成id,获取返回结果时通过此参数绑定请求结果与返回结果
     */
    private static PositiveAtomicCounter positiveAtomicCounter = new PositiveAtomicCounter();

    private final static ConcurrentMap<String, Channel> channels = new ConcurrentHashMap<>();

    private final static ConcurrentMap<String, Object> channelLocks = new ConcurrentHashMap<>();

    private static ChannelFuture connect;

    public static void setConnect(ChannelFuture connect) {
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
        Channel channelToServer = channels.get(serverAddress);
        if (channelIsOk(channelToServer)) {
            return channelToServer;
        }
        // 加锁操作
       channelLocks.putIfAbsent(serverAddress,new Object());
        synchronized (channelLocks.get(serverAddress)){
            return doConnect(serverAddress);
        }
    }

    private static Channel doConnect(String serverAddress) {
        Channel channelToServer = channels.get(serverAddress);
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
                    throw new RuntimeException("connect cancelled, can not connect to services-server.",connect.cause());
                } else if (!connect.isSuccess()) {
                    throw new RuntimeException("connect failed, can not connect to services-server.",connect.cause());
                } else {
                    channelFromPool = connect.channel();
                    channels.put(serverAddress,channelFromPool);
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
            channels.put(serverAddress, channelFromPool);
        } catch (Exception exx) {
            log.error("{} register RM failed.", serverAddress);
        }
        return channelFromPool;
    }

    public static BaseResponse sendMessageLoad(Object object,int type) throws TimeoutException{
        List<String> list = new ArrayList<>(channels.keySet());
        int size = list.size();
        if (size <= 0){
            return BaseResponse.error();
        }
        Random random = new Random();
        int i = random.nextInt(size - 1);
        Channel channel = channels.get(list.get(i));
        return sendMessage(object,type,channel);
    }

    public static BaseResponse sendMessage(Object object,int type) throws TimeoutException{
        if (connect!=null && connect.channel().isActive()){
            Channel channel = connect.channel();
            return sendMessage(object,type,channel);
        }else {
            return null;
        }
//        if (connect!=null && connect.channel().isActive()){
//            int nextId = positiveAtomicCounter.getAndIncrement();
//            RpcMessage rpcMessage = RpcMessage.builder().id(nextId).messageType(type).body(object).build();
//
//            MessageFuture messageFuture = new MessageFuture();
//            MAP.putIfAbsent(nextId,messageFuture);
//            ChannelFuture channelFuture = connect.channel().writeAndFlush(Unpooled.copiedBuffer(JSON.toJSONString(rpcMessage), CharsetUtil.UTF_8));
//            channelFuture.addListener((ChannelFutureListener) future -> {
//                if (!future.isSuccess()) {
//                    MessageFuture messageFuture1 = MAP.remove(rpcMessage.getId());
//                    if (messageFuture1 != null) {
//                        messageFuture1.setResultMessage(future.cause());
//                    }
//                    channelFuture.channel().close();
//                }
//            });
//            try {
//                Object o = messageFuture.get(1000, TimeUnit.MILLISECONDS);
//                if (o instanceof RpcMessage){
//                    RpcMessage result = (RpcMessage) o;
//                    if (result.getBody() instanceof BaseResponse){
//                        return (BaseResponse) result.getBody();
//                    }
//                }
//                return null;
//            } catch (Exception e) {
//                log.error("wait response error:{},ip:{},request:{}", e.getMessage(), "", JSON.toJSONString(object));
//                if (e instanceof TimeoutException) {
//                    throw (TimeoutException) e;
//                } else {
//                    throw new RuntimeException(e);
//                }
//            }
//        }else {
//            return null;
//        }
    }

    public static BaseResponse sendMessage(Object object,int type,Channel channel) throws TimeoutException{
        if (channel!=null && channel.isActive()){
            int nextId = positiveAtomicCounter.getAndIncrement();
            RpcMessage rpcMessage = RpcMessage.builder().id(nextId).messageType(type).body(object).build();

            MessageFuture messageFuture = new MessageFuture();
            MAP.putIfAbsent(nextId,messageFuture);
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
                Object o = messageFuture.get(1000, TimeUnit.MILLISECONDS);
                if (o instanceof RpcMessage){
                    RpcMessage result = (RpcMessage) o;
                    if (result.getBody() instanceof BaseResponse){
                        return (BaseResponse) result.getBody();
                    }
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
        }else {
            return null;
        }
    }
}
