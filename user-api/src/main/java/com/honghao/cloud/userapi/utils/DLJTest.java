package com.honghao.cloud.userapi.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.*;

/**
 * @author chenhonghao
 * @date 2019-11-15 13:39
 */
public class DLJTest {
    private static Logger logger = LoggerFactory.getLogger(DLJTest.class);
    public static DefaultHttpClient httpclient;
    static {
        try {
            httpclient = new DefaultHttpClient();
//            httpclient = ((DefaultHttpClient) HttpClientConnectionManager)
//                .getSSLInstance(httpclient); // 接受任何证书的浏览器客户端
        } catch (Exception ex) {

        }
    }

    /**
     * 生成端连接信息
     *
     * @author: lyh
     * @date: 2015年10月27日下午23:58:54
     */
    public static String  generateShortUrl(String url) {
        try {
            RestTemplate template = new RestTemplate();
            LinkedMultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
            parts.add("url", url);
            String result = template.postForObject("http://dwz.cn/admin/create", parts, String.class);
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject.getInteger("status") == 0 && jsonObject.containsKey("tinyurl")) {
                System.out.println(jsonObject.getString("tinyurl"));
            }
            return jsonObject.getString("tinyurl");
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }

    }
    private static List<NameValuePair> setHttpParams(Map<String, String> paramMap) {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        Set<Map.Entry<String, String>> set = paramMap.entrySet();
        for (Map.Entry<String, String> entry : set) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return formparams;
    }
    /**
     * 测试生成端连接
     * @param args
     * @author: lyh
     * @date: 2015年10月27日下午23:58:54
     */
    public static void main(String []args){
        String long_url = "";
        String appKey = "";
        String appkey = "";
        String result = null;
        CloseableHttpResponse response = null;
        try {
            //调用百度接口地址
            String baiduUrl = "https://www.mynb8.com/api2/dwz1";
            HttpGet httpGet=new HttpGet(baiduUrl);
            //拼接Get请求参数
            Map<String, String> headerMap=new HashMap<String, String>();
            //请到https://www.mynb8.com/user/login自行注册appkey
            headerMap.put("appkey", appkey);
            headerMap.put("long_url", long_url);
            List<NameValuePair> formparams = setHttpParams(headerMap);
            String param = URLEncodedUtils.format(formparams, "UTF-8");
            httpGet.setURI(URI.create(baiduUrl + "?" + param));
            //执行
            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            logger.error("处理失败 {}" + e);
            e.printStackTrace();
        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }

        }
//        return result;
    }
}
