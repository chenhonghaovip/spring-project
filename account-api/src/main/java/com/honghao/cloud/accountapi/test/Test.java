package com.honghao.cloud.accountapi.test;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

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

    @org.junit.Test
    public void test() throws IOException {
        //创建默认的httpClient实例.
        CloseableHttpClient httpclient = null;
        //接收响应结果
        CloseableHttpResponse response = null;
        try {
            //创建httppost
            httpclient = HttpClients.createDefault();
            String url ="http://192.168.16.36:8081/goSearch/gosuncn/deleteDocs.htm";
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE,"application/x-www-form-urlencoded");
            //参数
            String json ="{'ids':['html1','html2']}";
            StringEntity se = new StringEntity(json);
            se.setContentEncoding("UTF-8");
            se.setContentType("application/json");//发送json需要设置contentType
            httpPost.setEntity(se);
            response = httpclient.execute(httpPost);
            //解析返结果
            HttpEntity entity = response.getEntity();
            if(entity != null){
                String resStr = EntityUtils.toString(entity, "UTF-8");
                System.out.println(resStr);
            }
        } catch (Exception e) {
            throw e;
        }finally{
            httpclient.close();
            response.close();
        }
    }

    @org.junit.Test
    public void test1() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url("https:www.baidu.com")
                .build();
        Response execute = client.newCall(request).execute();
    }
}
