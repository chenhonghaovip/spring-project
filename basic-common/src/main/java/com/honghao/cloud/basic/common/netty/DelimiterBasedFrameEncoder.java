package com.honghao.cloud.basic.common.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

/**
 * 自定义编码器，主要作用是在返回的数据最后添加分隔符
 *
 * @author chenhonghao
 * @date 2020-10-29 09:45
 */
public class DelimiterBasedFrameEncoder extends MessageToByteEncoder<ByteBuf> {
    private String delimiter;


    public DelimiterBasedFrameEncoder(String delimiter) {
        this.delimiter = delimiter;
    }
//    @Override
//    protected void encode(ChannelHandlerContext channelHandlerContext, String s, ByteBuf byteBuf) {
//        channelHandlerContext.writeAndFlush(Unpooled.wrappedBuffer((s + delimiter).getBytes()));
//    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) {
        channelHandlerContext.writeAndFlush(Unpooled.wrappedBuffer((byteBuf.toString(CharsetUtil.UTF_8) + delimiter).getBytes()));
    }
}
