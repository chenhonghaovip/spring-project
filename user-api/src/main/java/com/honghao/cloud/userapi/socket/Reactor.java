package com.honghao.cloud.userapi.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * @author chenhonghao
 * @date 2020-07-17 21:10
 */
public class Reactor implements Runnable{
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    private Reactor() throws IOException {
        this.selector = Selector.open();
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.socket().setReuseAddress(true);
        this.serverSocketChannel.configureBlocking(false);
        this.serverSocketChannel.socket().bind(new InetSocketAddress(8000));

        SelectionKey register = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 将新连接处理器作为附件，绑定到register选择器
        register.attach(new AcceptorHandle());
        System.out.println("服务器启动");
    }


    public static void main(String[] args) throws IOException {
        Reactor nioServer = new Reactor();
        nioServer.run();
    }

    @Override
    public void run() {
        try {

            while (true){
                // 如果与SelectionKey相关的事件发生了，这个SelectionKey就被加入selected-keys集合中
                int read = selector.select();
                if (read==0){
                    continue;
                }
                SelectionKey selectionKey;
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    selectionKey = iterator.next();
                    dispatch(selectionKey);
                    //  接收连接就绪事件，表示服务器监听到了客户连接，服务器可以接收这个连接了。
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void dispatch(SelectionKey selectionKey){
        Runnable attachment = (Runnable) selectionKey.attachment();
        if (attachment!=null){
            attachment.run();
        }
    }


    private static class AcceptorHandle implements Runnable{

        @Override
        public void run() {
            // 接受新连接

            // 需要为新连接创建一个输入输出的handler处理器
        }
    }
}
