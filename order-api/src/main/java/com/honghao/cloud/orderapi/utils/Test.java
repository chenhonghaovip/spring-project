package com.honghao.cloud.orderapi.utils;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author chenhonghao
 * @date 2020-09-25 17:29
 */
public class Test {
    public static void main(String[] args) throws IOException {
        String url1 = "https://www.baidu.com/";
        URL url = new URL(url1);
        //得到connection对象。
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //设置请求方式
        connection.setRequestMethod("GET");
        //连接
        connection.connect();
        //得到响应码
        int responseCode = connection.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK){
            //得到响应流
            InputStream inputStream = connection.getInputStream();
        }

    }
}
