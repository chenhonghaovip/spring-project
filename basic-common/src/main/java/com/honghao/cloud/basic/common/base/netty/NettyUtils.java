package com.honghao.cloud.basic.common.base.netty;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.dto.MessageFuture;
import com.honghao.cloud.basic.common.base.dto.RpcMessage;
import com.honghao.cloud.basic.common.base.utils.PositiveAtomicCounter;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author chenhonghao
 * @date 2020-10-22 14:20
 */
@Slf4j
public class NettyUtils {
    public static final ConcurrentHashMap<Integer, MessageFuture> MAP = new ConcurrentHashMap<>();
    private static PositiveAtomicCounter positiveAtomicCounter = new PositiveAtomicCounter();
    public static final List<Server> LIST = new CopyOnWriteArrayList<>();
    private static ChannelFuture connect;

    public static void setConnect(ChannelFuture connect) {
        NettyUtils.connect = connect;
    }

    public static boolean hasConnection(String address){
        for (Server server : LIST) {
            if (address.equals(server.address)) {
                return channelIsOk(server.channel);
            }
        }
        return false;
    }

    private static boolean channelIsOk(Channel channel) {
        return channel != null && channel.isActive();
    }

    public synchronized static void put(String address, Channel channel) {
        Iterator<Server> it = LIST.iterator();
        boolean exist = false;
        while (it.hasNext()) {
            Server server = it.next();
            if (address.equals(server.address)) {
                server.channel = channel;
                exist = true;
                break;
            }
        }
        if (!exist) {
            Server server = new Server();
            server.address = address;
            server.channel = channel;
            LIST.add(server);
        }
    }

    /**
     * 移除那些在最新的worker地址集里没有的那些
     */
    public static void remove(String addresses) {
        LIST.remove(new Server(addresses,null));
    }

    public static BaseResponse sendMessage(Object object) throws TimeoutException{
        if (connect!=null && connect.channel().isActive()){
            int nextId = positiveAtomicCounter.getAndIncrement();
            RpcMessage rpcMessage = RpcMessage.builder().id(nextId).messageType(1).body(object).build();

            MessageFuture messageFuture = new MessageFuture();
            MAP.putIfAbsent(nextId,messageFuture);
            ChannelFuture channelFuture = connect.channel().writeAndFlush(Unpooled.copiedBuffer(JSON.toJSONString(rpcMessage), CharsetUtil.UTF_8));
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) {
                    if (!future.isSuccess()) {
                        MessageFuture messageFuture = MAP.remove(rpcMessage.getId());
                        if (messageFuture != null) {
                            messageFuture.setResultMessage(future.cause());
                        }
                        channelFuture.channel().close();
                    }
                }
            });
            try {
                Object o = messageFuture.get(5, TimeUnit.MILLISECONDS);
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


    private static class Server implements Comparable<Server> {
        private String address;
        private Channel channel;

        Server(String address, Channel channel) {
            this.address = address;
            this.channel = channel;
        }

        public Server() {
        }

        @Override
        public int compareTo(Server o) {
            //按address排序
            return this.address.compareTo(o.address);
        }

        @Override
        public String toString() {
            return "Server{" +
                    "address='" + address + '\'' +
                    ", channel=" + channel +
                    '}';
        }
    }
}
