package com.honghao.cloud.userapi.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.honghao.cloud.userapi.dto.common.ResultBean;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

/**
 * 新浪、百度、搜狐等官方长链接转短链接
 *
 * @author chenhonghao
 * @date 2019-11-15 13:10
 */
public class ConvertShortUrlUtil {
    public static CloseableHttpClient httpClient;

    static {
        httpClient = HttpClients.createDefault();
    }

    /**
     * 将长链接转为短链接(调用的缩短网址API)
     * 需要token
     * @param longUrl 需要转换的长链接url
     * @return 返回转换后的短链接
     */
    public static String longUrl2Short(String longUrl) {
        String result = null;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String temp = URLEncoder.encode(longUrl, "UTF-8");
            HttpGet request = new HttpGet("http://api.suolink.cn/api.php?url=" + temp);
            request.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)");

            HttpResponse response = client.execute(request);
            result = EntityUtils.toString(response.getEntity());
            if (!result.startsWith("http")) {
                result = null;
            }
        } catch (Exception e) {
            result = null;
        }
        if (StringUtils.isEmpty(result)) {
            result = longUrl;
        }
        return result;
    }

    /**
     * 将长链接转为短链接(调用的新浪的短网址API)
     * 需要source
     * @param url
     *        需要转换的长链接url
     * @return 返回转换后的短链接
     */
    public static ResultBean<String> convertSinaShortUrl(String url) {
        ResultBean<String> result = new ResultBean<>();
        try {
            // 调用新浪API
            HttpPost post = new HttpPost("http://api.t.sina.com.cn/short_url/shorten.json");
            List<NameValuePair> params = new LinkedList<NameValuePair>();
            // 必要的url长链接参数
            params.add(new BasicNameValuePair("url_long", url));
            // 必要的新浪key
            params.add(new BasicNameValuePair("source", "3271760578"));
            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            CloseableHttpResponse response = httpClient.execute(post);
            // 得到调用新浪API后成功返回的json字符串
            // url_short : 短链接地址 type：类型 url_long：原始长链接地址
            String json = EntityUtils.toString(response.getEntity(), "utf-8");
            JSONObject object;
            try {
                object = JSONObject.parseObject(json);
                if (object != null) {
                    result.setCode(object.getInteger("error_code"));
                    result.setMessage(object.getString("error"));
                }
            } catch (JSONException e) {
                JSONArray jsonArray = JSONArray.parseArray(json);
                object = (JSONObject) jsonArray.get(0);
                if (object != null) {
                    result.setCode(object.getInteger("type"));
                    result.setData(object.getString("url_short"));
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 将长链接转为短链接(调用的百度短网址API)
     * 需要 token
     * @param longUrl
     *            需要转换的长链接url
     * @return 返回转换后的短链接
     */
    public static ResultBean<String> convertBaiDuShortUrl(String longUrl) {
        ResultBean<String> result = new ResultBean<>();
        try {
            String uri = "https://dwz.cn/admin/v2/create";
            HttpPost httpost = new HttpPost(uri);
            httpost.addHeader("Token","dbbe913a3d0b7634a3f24526ce13a967");
            List<NameValuePair> params = new LinkedList<NameValuePair>();
            params.add(new BasicNameValuePair("Url", longUrl));
            // 用户名称
            httpost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            HttpResponse response = httpClient.execute(httpost);
            String json = EntityUtils.toString(response.getEntity(), "utf-8");
            JSONObject object = JSONObject.parseObject(json);
            if (object != null) {
                result.setCode(object.getInteger("Code"));
                result.setData(object.getString("ShortUrl"));
                result.setMessage(object.getString("ErrMsg"));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 将长链接转为短链接(调用的搜狐短网址API)
     * 需要 token
     * @param longUrl
     *            需要转换的长链接url
     * @return 返回转换后的短链接
     */
    public static ResultBean<String> convertSohuShortUrl(String longUrl) {
        ResultBean<String> result = new ResultBean<>();
        try {
            String uri = "https://sohu.gg/api?format=json";
            HttpPost httpost = new HttpPost(uri);
            httpost.addHeader("key","41ab8c3a");
            List<NameValuePair> params = new LinkedList<NameValuePair>();
            params.add(new BasicNameValuePair("url", longUrl));
            // 用户名称
            httpost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            HttpResponse response = httpClient.execute(httpost);
            String json = EntityUtils.toString(response.getEntity(), "utf-8");
            JSONObject object = JSONObject.parseObject(json);
            if (object != null) {
                result.setCode(object.getInteger("error"));
                result.setData(object.getString("short"));
                result.setMessage(object.getString("msg"));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 测试
     * @param args
     */
    public static void main(String []args){
        try {
            String wId = "2019111500000088";
            String temp = "MUdlAoWtseETdiCyuPmkFideqNuL1qTl";
            String sign = MD5Util.md5Encode(wId + temp.toUpperCase());

            ResultBean<String> resultBean = ConvertShortUrlUtil.convertBaiDuShortUrl("http://uat.ydp.yundasys.com/cloudhts/msgLink/toHtml" + "?sign=" + sign + "&orderId=" + wId);
            if (!org.springframework.util.StringUtils.isEmpty(resultBean)){
                String shortUrl = resultBean.getData();
                System.out.println(shortUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultBean<String> result = convertSinaShortUrl("http://www.baidu.com");
        System.out.println(result);
    }
}
