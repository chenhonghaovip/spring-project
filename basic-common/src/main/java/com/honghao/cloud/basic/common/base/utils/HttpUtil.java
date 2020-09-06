package com.honghao.cloud.basic.common.base.utils;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author wsfeng
 *
 */
public class HttpUtil {
	
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);


	public static String httpPost(String url, String enity) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		StringEntity stringEntity = new StringEntity(enity, "UTF-8");
		stringEntity.setContentType("application/json");
		post.setEntity(stringEntity);
		try {
			CloseableHttpResponse response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, "utf-8");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public static BaseResponse<List<String>> doPost(String url, String json, int seconds){
		seconds = seconds*1000;
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(seconds).setConnectionRequestTimeout(seconds).setSocketTimeout(seconds).build();
		post.setConfig(requestConfig);
        BaseResponse<List<String>> response = BaseResponse.error();
		try {
			StringEntity s = new StringEntity(json,"UTF-8");
			s.setContentType("application/json");
			post.setEntity(s);
			HttpResponse res = client.execute(post);
			if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				String result = EntityUtils.toString(res.getEntity());
				response = JSON.parseObject(result,BaseResponse.class);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;
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
	
	/**
	 * @param url 请求地址
	 * @param seconds 等待时长 秒
	 * @return String
	 */
	public static String httpGetWithPair(String url,int seconds){
		seconds = seconds*1000;
		CloseableHttpClient httpClient = HttpClients.createDefault(); 
		HttpGet httpGet = new HttpGet(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(seconds).setConnectionRequestTimeout(seconds).setSocketTimeout(seconds).build();
		httpGet.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpGet.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String resultStr = EntityUtils.toString(entity);
			return resultStr;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}finally{
			if(response != null){
				try {
					response.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return null;
	}
	
	public static String httpPostWithPair(String url){
		CloseableHttpClient httpClient = HttpClients.createDefault(); 
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String resultStr = EntityUtils.toString(entity);
			return resultStr;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}finally{
			if(response != null){
				try {
					response.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return null;
	}
	
	public static String httpPostWithPair1(String url, Map<String, String> enity,int time) {
		String content = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(time).setSocketTimeout(time).setConnectTimeout(time).build();
		post.setConfig(requestConfig);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		Iterator<Map.Entry<String, String>> iterator = enity.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> tmp = iterator.next();
			list.add(new BasicNameValuePair(tmp.getKey(), tmp.getValue()));
		}
		CloseableHttpResponse response = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
			response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			content = EntityUtils.toString(entity, "utf-8");
		}catch (Exception e) {
			logger.error("httpPostWithPair接口请求操作失败",e);
		}finally {
			if (response != null) {
				try {
					response.close();
				}catch (IOException e) {
					logger.error("操作失败",e);
				}
			}
			try {
				httpClient.close();
			}catch (IOException e) {
				logger.error("操作失败",e);
			}
		}
		return content;
	}

	public static String doHttpPostJson(String url, String enity,int time) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(time).setConnectionRequestTimeout(time).setSocketTimeout(time).build();
		try {
			InputStreamEntity stringEntity = new InputStreamEntity(new ByteArrayInputStream(enity.getBytes("utf-8")));
			stringEntity.setContentType("application/json;charset=utf8");
			post.setConfig(requestConfig);
			post.setEntity(stringEntity);
			CloseableHttpResponse response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, "utf-8");
		}catch (Exception e) {
			logger.error("httpPost接口请求操作失败",e);
		}
		return null;
	}
}
