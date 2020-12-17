package com.honghao.cloud.basic.common.netty;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.basic.common.dto.MessageFuture;
import com.honghao.cloud.basic.common.dto.ProtocolConstants;
import com.honghao.cloud.basic.common.dto.RpcMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

import java.util.Objects;


/**
 * @author chenhonghao
 * @date 2020-10-22 11:41
 */
@ChannelHandler.Sharable
public class NettyClientHandle extends SimpleChannelInboundHandler<ByteBuf> {
    /**
     * 超时检测
     *
     * @param ctx ctx
     * @param evt evt
     * @throws Exception Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            // 判断超市事件类型
            if (idleStateEvent.state() == IdleState.ALL_IDLE) {
                //向服务端发送心跳消息检测（messageType = 6,id = -1）
                RpcMessage rpcMessage = RpcMessage.builder().messageType(ProtocolConstants.HEART_BEAT).id(-1).build();
                ctx.writeAndFlush(Unpooled.copiedBuffer(JSON.toJSONString(rpcMessage), CharsetUtil.UTF_8));
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf s) {
        String s1 = s.toString(CharsetUtil.UTF_8);
        System.out.println("接收到消息为" + s1);
        RpcMessage rpcMessage = JSON.parseObject(s1, RpcMessage.class);

        MessageFuture messageFuture;
        if (Objects.nonNull(messageFuture = NettyUtils.MAP.remove(rpcMessage.getId()))) {
            messageFuture.setResultMessage(rpcMessage);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        NettyUtils.releaseChannel(ctx.channel(), NettyUtils.toStringAddress(ctx.channel().remoteAddress()));
    }

    /**
     * 检测连接活动情况，只会在通道建立时调用一次
     *
     * @param ctx ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("和服务器端建立连接");
    }

    /**
     * 检测连接非活动情况，只会在通道失效时调用一次（服务端失败时）
     *
     * @param ctx ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("和服务器端连接掉线");
        NettyUtils.releaseChannel(ctx.channel(), NettyUtils.toStringAddress(ctx.channel().remoteAddress()));
    }
}
