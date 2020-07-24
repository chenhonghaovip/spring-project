package com.honghao.cloud.userapi.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author chenhonghao
 * @date 2020-07-17 20:24
 */
public class BIOServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(9010)){
            while (true){
                Socket socket = serverSocket.accept();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                String msg;
                while ((msg = bufferedReader.readLine())!=null){
                    System.out.println(msg);
                }
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
