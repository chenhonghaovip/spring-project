package com.honghao.cloud.message.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chenhonghao
 * @date 2020-10-21 17:21
 */
@Component
public class SendClientHandler extends ChannelInboundHandlerAdapter {
    private ChannelHandlerContext channelHandlerContext;
    /**
     * 当客户端和服务端TCP链路建立成功之后，Netty的NIO线程会调用channelActive方法，
     * 发送查询时间的指令给服务端。
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //将请求信息发送给服务端
//        ctx.writeAndFlush(firstMessage);
    }

    /**
     * 当服务端返回应答消息时调用channelRead方法
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 读取消息
        ByteBuf buf = (ByteBuf) msg;
        byte[] resp = new byte[buf.readableBytes()];
        buf.readBytes(resp);
        // 释放资源
        ReferenceCountUtil.release(msg);
        String respMsg = new String(resp, StandardCharsets.UTF_8);
        System.out.println(respMsg);

    }

    /**
     * 发生异常时，打印异常日志，释放客户端资源。
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }


    private void sendDataToServer(Channel channel) throws Exception {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd,HH:mm:ss:SSS");

        while (true) {
            date.setTime(System.currentTimeMillis());
            channel.writeAndFlush("客户端@" + sdf.format(date));
            System.out.println("客户端发送数据:" + sdf.format(date));
            Thread.sleep(1000);
        }
    }

}
