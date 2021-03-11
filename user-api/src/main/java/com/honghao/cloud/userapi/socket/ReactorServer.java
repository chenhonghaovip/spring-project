package com.honghao.cloud.userapi.socket;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author chenhonghao
 * @date 2020-07-17 21:10
 */
public class ReactorServer implements Runnable {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    private ReactorServer() throws IOException {
        this.selector = Selector.open();
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.socket().setReuseAddress(true);
        this.serverSocketChannel.configureBlocking(false);
        this.serverSocketChannel.socket().bind(new InetSocketAddress(8000));

        SelectionKey register = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 将新连接处理器作为附件，绑定到register选择器
        register.attach(new AcceptorHandle(serverSocketChannel, selector));
        System.out.println("服务器启动");
    }


    public static void main(String[] args) throws IOException {
        ReactorServer nioServer = new ReactorServer();
        nioServer.run();
    }

    @Override
    public void run() {
        try {
            while (true) {
                // 如果与SelectionKey相关的事件发生了，这个SelectionKey就被加入selected-keys集合中
                // 若是没有事件发生时，则阻塞，直到有事件发生被唤醒
                int read = selector.select();
                if (read == 0) {
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

    void dispatch(SelectionKey selectionKey) {
        Runnable attachment = (Runnable) selectionKey.attachment();
        if (attachment != null) {
            attachment.run();
        }
    }


    private static class AcceptorHandle implements Runnable {
        private final Selector selector;
        private final ServerSocketChannel serverSocketChannel;

        public AcceptorHandle(ServerSocketChannel serverSocketChannel, Selector selector) {
            this.serverSocketChannel = serverSocketChannel;
            this.selector = selector;
        }

        @Override
        public void run() {
            try {
                // 接受client新连接请求
                SocketChannel accept = serverSocketChannel.accept();
                if (accept != null) {
                    System.out.println("连接建立");
                    // 设置为非阻塞
                    accept.configureBlocking(false);
                    // 将read注册到selector中，对应的handle处理器为ReadHandle，socket为accept
                    SelectionKey register = accept.register(selector, SelectionKey.OP_READ);
                    // 使一个阻塞住的selector操作立即返回
                    selector.wakeup();
                    register.attach(new ReadHandle(accept, register));
                }
                // 需要为新连接创建一个输入输出的handler处理器
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ReadHandle implements Runnable {
        private final SocketChannel socketChannel;
        private final SelectionKey selectionKey;

        public ReadHandle(SocketChannel socketChannel, SelectionKey selectionKey) {
            this.socketChannel = socketChannel;
            this.selectionKey = selectionKey;
        }
        @Override
        public void run() {
            try {
                read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private synchronized void read() throws IOException {
            byte[] arr = new byte[1024];
            ByteBuffer buffer = ByteBuffer.wrap(arr);

            int read = socketChannel.read(buffer);
            if (read == -1) {
                cloesChannel();
                return;
            }
            // 将读取到的byte转化为string字符串
            String str = new String(arr);
            System.out.println("发送请求数据：" + str);
            if (StringUtils.isNotBlank(str)) {
                // 处理业务逻辑
                process(str);
                // 根据读取的数据返回响应
                send();
                // 使一个阻塞住的selector操作立即返回
                selectionKey.selector().wakeup();
            }
        }

        private void cloesChannel() throws IOException {
            socketChannel.close();
        }

        private void process(String str) {
            System.out.println("开始处理逻辑" + str);
        }

        /**
         * 返回处理结果到客户端
         *
         * @throws IOException IOException
         */
        private void send() throws IOException {
            String result = "逻辑处理结束，结果正确";
            ByteBuffer byteBuffer = ByteBuffer.wrap(result.getBytes());
            socketChannel.write(byteBuffer);
            socketChannel.close();
        }
    }
}
