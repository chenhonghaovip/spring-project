package com.honghao.cloud.userapi.socket;

import com.honghao.cloud.basic.common.factory.ThreadPoolFactory;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chenhonghao
 * @date 2020-07-17 21:10
 */
public class ReactorThreadServer implements Runnable {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    private ReactorThreadServer() throws IOException {
        this.selector = Selector.open();
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.socket().setReuseAddress(true);
        this.serverSocketChannel.configureBlocking(false);
        this.serverSocketChannel.socket().bind(new InetSocketAddress(8000));

        // 建立一个监听serverSocketChannel中SelectionKey.OP_ACCEPT时间的选择器
        SelectionKey register = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 将新连接处理器作为附件，绑定到register选择器
        register.attach(new AcceptorHandle(serverSocketChannel));
        System.out.println("服务器启动");
    }


    public static void main(String[] args) throws IOException {
        ReactorThreadServer nioServer = new ReactorThreadServer();
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

    static void dispatch(SelectionKey selectionKey) {
        Runnable attachment = (Runnable) selectionKey.attachment();
        if (attachment != null) {
            attachment.run();
        }
    }


    private static class AcceptorHandle implements Runnable {
        private static final int core = Runtime.getRuntime().availableProcessors();
        private static final ThreadPoolExecutor EXECUTOR = ThreadPoolFactory.buildThreadPoolExecutor(core, core, "runable");
        private final ServerSocketChannel serverSocketChannel;
        private final Thread[] threads = new Thread[core];
        private final Other[] others = new Other[core];
        private final Selector[] selectors = new Selector[core];

        public AcceptorHandle(ServerSocketChannel serverSocketChannel) throws IOException {
            this.serverSocketChannel = serverSocketChannel;
            // 循环创建多个selector，这些的selector只接受read类型的注册
            for (int i = 0; i < core; i++) {
                Selector open = Selector.open();
                selectors[i] = open;
                others[i] = new Other(serverSocketChannel,open);
                threads[i] = new Thread(others[i]);
                threads[i].start();
            }
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

                    int l = new Long(System.currentTimeMillis() % core).intValue();

                    others[l].setReStart(true);
                    selectors[l].wakeup();
                    // 将read注册到selector中，对应的handle处理器为ReadHandle，socket为accept
                    // 将accept通道注册到selectors中监听，事件类型为SelectionKey.OP_READ
                    SelectionKey register = accept.register(selectors[l], SelectionKey.OP_READ);
                    // 使一个阻塞住的selector操作立即返回
                    selectors[l].wakeup();
                    others[l].setReStart(false);
                    register.attach(new ReadHandle(accept, selectors[l]));
                }
                // 需要为新连接创建一个输入输出的handler处理器
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ReadHandle implements Runnable {
        private final SocketChannel socketChannel;
        private final Selector selector;

        public ReadHandle(SocketChannel socketChannel, Selector selector) {
            this.socketChannel = socketChannel;
            this.selector = selector;
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
                selector.wakeup();
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



    @Data
    private static class Other implements Runnable {
        private final ServerSocketChannel serverSocketChannel;
        private final Selector selector;
        private boolean reStart = false;

        public Other(ServerSocketChannel serverSocketChannel, Selector selector) {
            this.serverSocketChannel = serverSocketChannel;
            this.selector = selector;
        }

        @Override
        public void run() {
            try {
                while (true){
                    if (!reStart){
                        selector.select();

                        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                        while (iterator.hasNext()){
                            SelectionKey next = iterator.next();
                            ((Runnable)next.attachment()).run();
                            iterator.remove();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
