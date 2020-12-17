package com.honghao.cloud.message.netty;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.basic.common.dto.ProtocolConstants;
import com.honghao.cloud.basic.common.dto.RpcMessage;
import com.honghao.cloud.message.controller.MessageController;
import com.honghao.cloud.message.dto.BatchMsgInfoDTO;
import com.honghao.cloud.message.dto.MsgInfoDTO;
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

    public static void main(String[] args) {
        String request = "{\"body\":{\"msgList\":[{\"appId\":\"order-api\",\"businessId\":\"2020051200000003\",\"content\":\"{\\\"batchId\\\":\\\"2020051200000002\\\",\\\"createDate\\\":1589212800000,\\\"createTime\\\":1589247784000,\\\"deliveryFee\\\":0.00,\\\"deliveryMode\\\":1,\\\"etArriveTime\\\":1589250100000,\\\"expectPrices\\\":0.00,\\\"isTimely\\\":0,\\\"orderPayStatus\\\":1,\\\"orderPayment\\\":0,\\\"orderRemark\\\":\\\"\\\",\\\"orderSource\\\":0,\\\"orderStatus\\\":0,\\\"orderTime\\\":1589247765000,\\\"orderType\\\":1,\\\"receiveAdcode\\\":\\\"310112\\\",\\\"receiveAddress\\\":\\\"七宝老街拒绝\\\",\\\"receiveCity\\\":\\\"上海市\\\",\\\"receiveCounty\\\":\\\"闵行区\\\",\\\"receiveLatitude\\\":31.151909,\\\"receiveLongitude\\\":121.35466,\\\"receiveName\\\":\\\"江帅\\\",\\\"receivePhone\\\":\\\"13761853396\\\",\\\"receiveProvince\\\":\\\"上海市\\\",\\\"sendAdcode\\\":\\\"310118\\\",\\\"sendAddress\\\":\\\"诸光路1588弄虹桥世界中心L2栋一层505荟盈Show鹅阿柯\\\",\\\"sendCity\\\":\\\"上海市\\\",\\\"sendCounty\\\":\\\"青浦区\\\",\\\"sendLatitude\\\":31.184059,\\\"sendLongitude\\\":121.305732,\\\"sendName\\\":\\\"吉吉国王\\\",\\\"sendPhone\\\":\\\"13761853396\\\",\\\"sendProvince\\\":\\\"上海市\\\",\\\"serialNumber\\\":\\\"1\\\",\\\"shipId\\\":\\\"0\\\",\\\"totalPrices\\\":0.00,\\\"updateTime\\\":1589247784000,\\\"userId\\\":\\\"2019101200000003\\\",\\\"wId\\\":\\\"2020051200000003\\\"}\",\"status\":0,\"topic\":\"create_order\",\"url\":\"/order/batchQuery\"},{\"appId\":\"order-api\",\"businessId\":\"2020051200000006\",\"content\":\"{\\\"batchId\\\":\\\"2020051200000005\\\",\\\"createDate\\\":1589040000000,\\\"createTime\\\":1589249068000,\\\"deliveryFee\\\":0.00,\\\"deliveryMode\\\":1,\\\"etArriveTime\\\":1589251201000,\\\"expectPrices\\\":0.00,\\\"isTimely\\\":0,\\\"orderPayStatus\\\":2,\\\"orderPayment\\\":4,\\\"orderRemark\\\":\\\"\\\",\\\"orderSource\\\":0,\\\"orderStatus\\\":0,\\\"orderTime\\\":1589249031000,\\\"orderType\\\":1,\\\"receiveAdcode\\\":\\\"310112\\\",\\\"receiveAddress\\\":\\\"虹桥天地购物中心3\\\",\\\"receiveCity\\\":\\\"上海市\\\",\\\"receiveCounty\\\":\\\"闵行区\\\",\\\"receiveLatitude\\\":31.1927109,\\\"receiveLongitude\\\":121.3153992,\\\"receiveName\\\":\\\"科学出版社\\\",\\\"receivePhone\\\":\\\"13312525588\\\",\\\"receiveProvince\\\":\\\"上海市\\\",\\\"sendAdcode\\\":\\\"310118\\\",\\\"sendAddress\\\":\\\"诸光路1588弄虹桥世界中心L2栋一层505荟盈Show鹅阿柯\\\",\\\"sendCity\\\":\\\"上海市\\\",\\\"sendCounty\\\":\\\"青浦区\\\",\\\"sendLatitude\\\":31.184059,\\\"sendLongitude\\\":121.305732,\\\"sendName\\\":\\\"吉吉国王\\\",\\\"sendPhone\\\":\\\"13761853396\\\",\\\"sendProvince\\\":\\\"上海市\\\",\\\"serialNumber\\\":\\\"5\\\",\\\"shipId\\\":\\\"0\\\",\\\"totalPrices\\\":2.69,\\\"updateTime\\\":1589249068000,\\\"userId\\\":\\\"2019101200000003\\\",\\\"wId\\\":\\\"2020051200000006\\\"}\",\"status\":0,\"topic\":\"create_order_1\",\"url\":\"/order/batchQuery\"}]},\"id\":1,\"messageType\":2}";
        System.out.println(request.length());
        RpcMessage rpcMessage = JSON.parseObject(request, RpcMessage.class);
        System.out.println(rpcMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        String request = buf.toString(CharsetUtil.UTF_8);
        System.out.println("接收客户端到消息为" + request);

        RpcMessage rpcMessage = JSON.parseObject(request, RpcMessage.class);
        if (Objects.nonNull(rpcMessage)) {
            // 判断消息类型，进行不同的逻辑处理
            int messageType = rpcMessage.getMessageType();
            Object body = rpcMessage.getBody();
            BaseResponse result = BaseResponse.success();

            MsgInfoDTO msgInfoDTO = JSON.parseObject(JSON.toJSONString(body), MsgInfoDTO.class);
            BatchMsgInfoDTO batchMsgInfoDTO = JSON.parseObject(JSON.toJSONString(body), BatchMsgInfoDTO.class);
            switch (messageType) {
                case ProtocolConstants.HEART_BEAT:
                    // 消息心跳处理
                    // TODO: 2020/10/23
                    break;
                case ProtocolConstants.INSERT:
                    result = messageController.saveMessage(msgInfoDTO);
                    break;
                case ProtocolConstants.UPDATE:
                    result = messageController.send(msgInfoDTO);
                    break;
                case ProtocolConstants.BATCH_INSERT:
                    result = messageController.batchSaveMessage(batchMsgInfoDTO);
                    break;
                case ProtocolConstants.BATCH_UPDATE:
                    result = messageController.batchSend(batchMsgInfoDTO);
                    break;
                case ProtocolConstants.COMPLETE:
                    result = messageController.complete(msgInfoDTO);
                    break;
                default:
            }
            rpcMessage.setBody(result);
            // 返回响应结果
            ctx.writeAndFlush(Unpooled.copiedBuffer(JSON.toJSONString(rpcMessage), CharsetUtil.UTF_8));
        }
    }

    /**
     * @param ctx   ctx
     * @param cause cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("连接异常");
//        ctx.close();
    }

    /**
     * 新建立连接时触发动作
     *
     * @param ctx ctx
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        System.out.println("新建立连接时触发动作");
        channels.add(ctx.channel());
    }

    /**
     * 连接断开时触发动作
     *
     * @param ctx ctx
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        System.out.println("连接断开时触发动作");
        channels.remove(ctx.channel());
    }

    /**
     * 检测连接活动情况，只会在通道建立时调用一次
     *
     * @param ctx ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("检测连接活动情况，只会在通道建立时调用一次");
    }

    /**
     * 检测连接非活动情况，只会在通道失效时调用一次
     *
     * @param ctx ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("检测连接非活动情况，只会在通道失效时调用一次");
    }
}
