package com.honghao.cloud.userapi.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author chenhonghao
 * @date 2020-07-17 20:28
 */
@Slf4j
public class ReactorClient extends Thread {
    private String host;

    private int port;

    public ReactorClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) {
        ReactorClient bioClient = new ReactorClient("localhost", 8000);
        bioClient.start();
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(host, port); OutputStream o = socket.getOutputStream()) {
            Scanner scanner = new Scanner(System.in);
            String msg = scanner.nextLine();
            o.write(msg.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
