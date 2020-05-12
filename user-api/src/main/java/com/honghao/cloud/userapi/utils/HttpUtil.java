package com.honghao.cloud.userapi.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 
 * @author wsfeng
 *
 */
@Slf4j
public class HttpUtil {
	public static String httpPost(String url, String enity) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		StringEntity stringEntity = new StringEntity(enity, "UTF-8");
		stringEntity.setContentType("application/json");
		post.setEntity(stringEntity);
		try {
			CloseableHttpResponse response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity, "utf-8");
			return content;
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public static String doPost(String url,int seconds) {
		seconds = seconds*1000;
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(seconds).setConnectionRequestTimeout(seconds)
				.setSocketTimeout(seconds).build();
		post.setConfig(requestConfig);
		String result = "";
		try {
			HttpResponse res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				result = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	public static JSONObject doPost(String url,String json,int seconds){
		seconds = seconds*1000;
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(seconds).setConnectionRequestTimeout(seconds).setSocketTimeout(seconds).build();
		post.setConfig(requestConfig);
		JSONObject response = null;
		try {
			StringEntity s = new StringEntity(json,"UTF-8");
			s.setContentType("application/json");
			post.setEntity(s);
			HttpResponse res = client.execute(post);
			if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				String result = EntityUtils.toString(res.getEntity());
				response = JSONObject.parseObject(result);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;
	}
}
