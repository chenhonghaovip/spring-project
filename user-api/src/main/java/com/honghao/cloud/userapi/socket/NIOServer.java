package com.honghao.cloud.userapi.socket;

import com.honghao.cloud.basic.common.base.factory.ThreadPoolFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chenhonghao
 * @date 2020-07-17 21:10
 */
public class NIOServer {
    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(10,50,"test001");
    public static void main(String[] args) throws IOException {

        // 为ServerSocketChannel监控接收连接就绪事件，为SocketChannel监控连接就绪、读就绪和写就绪事件。
        Selector selector = Selector.open();

        // ServerSocket的替代类，支持阻塞通信与非阻塞通信。
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
//        serverSocketChannel.bind(new InetSocketAddress(9200));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(9200));
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
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey selectionKey : selectionKeys) {
                try {
                    ServerSocketChannel socketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel accept = socketChannel.accept();
                    //  接收连接就绪事件，表示服务器监听到了客户连接，服务器可以接收这个连接了。
                    if (selectionKey.isAcceptable()){
                        accept.configureBlocking(false);
                        accept.register(selector,SelectionKey.OP_CONNECT);
                    }
                    // 读就绪事件，表示输入流中已经有了可读数据，可以执行读操作了。
                    else if (selectionKey.isReadable()){

                    }
                    // 写就绪事件，表示已经可以向输出流写数据了。
                    else if (selectionKey.isWritable()){

                    }
                    // 连接就绪事件，表示客户与服务器的连接已经建立成功。
                    else if (selectionKey.isConnectable()){

                    }
                } catch (IOException e) {
                    selectionKey.cancel();
                    selectionKey.channel().close();
                }
            }
        }
    }
}
