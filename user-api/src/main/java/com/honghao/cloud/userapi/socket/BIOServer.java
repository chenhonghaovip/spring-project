package com.honghao.cloud.userapi.socket;

import com.honghao.cloud.basic.common.factory.ThreadPoolFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chenhonghao
 * @date 2020-07-17 20:24
 */
public class BIOServer {
    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(10, 100, "BIO");

    public static void main(String[] args) throws IOException {
        // 创建socket服务,打开并监听9010端口
        ServerSocket serverSocket = new ServerSocket(9010);
        while (true) {
            // 获取一个套接字,接受客户端连接（阻塞） -- 直到获取到一个连接之后（第一次阻塞）
            final Socket socket = serverSocket.accept();
            threadPoolExecutor.execute(() -> business(socket));
        }
    }

    private static void business(Socket socket) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            String msg;
            // 读写时进行阻塞操作（第二次阻塞 -- 只要连接没有数据时，就一直阻塞）
            while ((msg = bufferedReader.readLine()) != null) {
                System.out.println(Thread.currentThread().getName() + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭套接字
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
