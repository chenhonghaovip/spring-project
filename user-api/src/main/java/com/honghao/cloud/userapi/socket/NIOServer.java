package com.honghao.cloud.userapi.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author chenhonghao
 * @date 2020-07-17 21:10
 */
public class NIOServer {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private Charset charset = Charset.forName("GBK");

    private NIOServer() throws IOException {
        this.selector = Selector.open();
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.socket().setReuseAddress(true);
        this.serverSocketChannel.configureBlocking(false);
        int port = 8000;
        this.serverSocketChannel.socket().bind(new InetSocketAddress(port));
        System.out.println("服务器启动");
    }


    public static void main(String[] args) throws IOException {
        NIOServer nioServer = new NIOServer();
        nioServer.run();
    }

    private void run() throws IOException {
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            // 如果与SelectionKey相关的事件发生了，这个SelectionKey就被加入selected-keys集合中
            int read = selector.select();
            if (read==0){
                continue;
            }

            // 代表ServerSocketChannel以及SocketChannel向Selector注册事件的句柄。
            // 当一个SelectionKey对象位于Selector对象的selected-keys集合中，就表示与这个SelectionKey对象相关的事件发生了。
            // 获取相关事件已经被Selector捕获的SelectionKey的集合
            SelectionKey selectionKey = null ;
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                try {
                    selectionKey = iterator.next();
                    //  接收连接就绪事件，表示服务器监听到了客户连接，服务器可以接收这个连接了。
                    if (selectionKey.isAcceptable()){
                        ServerSocketChannel socketChannel = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel accept = socketChannel.accept();
                        System.out.println("接收连接就绪事件:"+socketChannel.socket().getInetAddress()+":port="+socketChannel.socket().getLocalPort());
                        accept.configureBlocking(false);
                        // 创建一个用户发送来的数据的缓冲区
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        accept.register(selector, SelectionKey.OP_READ ,byteBuffer);
                    }
                    // 读就绪事件，表示输入流中已经有了可读数据，可以执行读操作了。
                    else if (selectionKey.isReadable()){
                        System.out.println("读就绪事件");
                        // 创建一个用户发送来的数据的缓冲区
                        this.receive(selectionKey);
                    }
                    // 写就绪事件，表示已经可以向输出流写数据了。
                    else if (selectionKey.isWritable()){
                        System.out.println("写就绪事件");
                        this.send(selectionKey);
                    }
                    // 连接就绪事件，表示客户与服务器的连接已经建立成功。
                    else if (selectionKey.isConnectable()){
                        System.out.println("连接就绪事件");
                    }
                    iterator.remove();
                } catch (IOException e) {
                    selectionKey.cancel();
                    selectionKey.channel().close();

                }
            }
        }
    }

    private void send(SelectionKey key) throws IOException {
        ByteBuffer buffer = (ByteBuffer)key.attachment();
        SocketChannel socketChannel = (SocketChannel)key.channel();
        buffer.flip();
        String data = this.decode(buffer);
        if (data.indexOf("\r\n") != -1) {
            String outputData = data.substring(0, data.indexOf("\n") + 1);
            System.out.print(outputData);
            ByteBuffer outputBuffer = this.encode("echo:" + outputData);

            while(outputBuffer.hasRemaining()) {
                socketChannel.write(outputBuffer);
            }

            ByteBuffer temp = this.encode(outputData);
            buffer.position(temp.limit());
            buffer.compact();
            if (outputData.equals("bye\r\n")) {
                key.cancel();
                socketChannel.close();
                System.out.println("关闭与客户的连接");
            }

        }
    }

    private void receive(SelectionKey key) throws IOException {
        ByteBuffer buffer = (ByteBuffer)key.attachment();
        SocketChannel socketChannel = (SocketChannel)key.channel();
        ByteBuffer readBuff = ByteBuffer.allocate(32);
        socketChannel.read(readBuff);
        readBuff.flip();
        buffer.limit(buffer.capacity());
        buffer.put(readBuff);
        System.out.println(readBuff.get());
    }

    private String decode(ByteBuffer buffer) {
        CharBuffer charBuffer = this.charset.decode(buffer);
        return charBuffer.toString();
    }

    private ByteBuffer encode(String str) {
        return this.charset.encode(str);
    }
}
