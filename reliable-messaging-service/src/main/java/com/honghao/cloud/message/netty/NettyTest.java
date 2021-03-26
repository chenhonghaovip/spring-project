package com.honghao.cloud.message.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedFile;
import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author chenhonghao
 * @date 2021-03-17 11:07
 */
public class NettyTest {

    @Test
    public void test() throws IOException {
        String srcFileName = "E:\\file\\input.txt";
        String destFileName = "E:\\file\\output.txt";
        // 通过RandomAccessFile打开一个文件
        RandomAccessFile srcFile = new RandomAccessFile(srcFileName, "r");
        FileChannel srcFileChannel = srcFile.getChannel();

        RandomAccessFile destFile = new RandomAccessFile(destFileName, "rw");
        FileChannel destFileChannel = destFile.getChannel();

        long position = 0;
        long count = srcFileChannel.size();

        ServerSocketChannel open = ServerSocketChannel.open();
        SocketChannel socketChannel = open.accept();

        srcFileChannel.transferTo(position, count, socketChannel);
    }

    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        RandomAccessFile raf = null;
        long length = -1;
        try {
            // 1. 通过 RandomAccessFile 打开一个文件.
            raf = new RandomAccessFile(msg, "r");
            length = raf.length();
        } catch (Exception e) {
            ctx.writeAndFlush("ERR: " + e.getClass().getSimpleName() + ": " + e.getMessage() + '\n');
            return;
        } finally {
            if (length < 0 && raf != null) {
                raf.close();
            }
        }

        ctx.write("OK: " + raf.length() + '\n');
        if (ctx.pipeline().get(SslHandler.class) == null) {
            // SSL not enabled - can use zero-copy file transfer.
            // 2. 调用 raf.getChannel() 获取一个 FileChannel.
            // 3. 将 FileChannel 封装成一个 DefaultFileRegion
            ctx.write(new DefaultFileRegion(raf.getChannel(), 0, length));
        } else {
            // SSL enabled - cannot use zero-copy file transfer.
            ctx.write(new ChunkedFile(raf));
        }
        ctx.writeAndFlush("\n");
    }
}
