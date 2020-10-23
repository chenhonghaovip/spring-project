package com.honghao.cloud.message.netty;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.dto.ProtocolConstants;
import com.honghao.cloud.basic.common.base.dto.RpcMessage;
import com.honghao.cloud.message.controller.MessageController;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Objects;

/**
 * @author chenhonghao
 * @date 2020-10-22 14:00
 */
@ChannelHandler.Sharable
public class NettyServerHandle extends ChannelInboundHandlerAdapter {
    public static MessageController messageController;

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf)msg;
        String request = buf.toString(CharsetUtil.UTF_8);
        System.out.println("接收客户端到消息为" + request);

        RpcMessage rpcMessage = JSON.parseObject(request, RpcMessage.class);
        if (Objects.nonNull(rpcMessage)){
            // 判断消息类型，进行不同的逻辑处理
            if (ProtocolConstants.HEART_BEAT == rpcMessage.getMessageType()){
                // 消息心跳处理
            }
        }
        BaseResponse result = BaseResponse.successData("123456");
        rpcMessage.setBody(result);
        ctx.writeAndFlush(Unpooled.copiedBuffer(JSON.toJSONString(rpcMessage), CharsetUtil.UTF_8));
    }


    /**
     *
     * @param  ctx ctx
     * @param cause cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("连接异常");
        ctx.close();
    }

    /**
     * 新建立连接时触发动作
     * @param ctx ctx
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        System.out.println("新建立连接时触发动作");
        channels.add(ctx.channel());
    }
    /**
     * 连接断开时触发动作
     * @param ctx ctx
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        System.out.println("连接断开时触发动作");
        channels.remove(ctx.channel());
    }

    /**
     * 检测连接活动情况，只会在通道建立时调用一次
     * @param ctx ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("检测连接活动情况，只会在通道建立时调用一次");
    }

    /**
     * 检测连接非活动情况，只会在通道失效时调用一次
     * @param ctx ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("检测连接非活动情况，只会在通道失效时调用一次");
    }
}
