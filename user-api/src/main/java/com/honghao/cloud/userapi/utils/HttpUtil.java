package com.honghao.cloud.userapi.utils;

import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.userapi.aspect.DTOT;
import com.honghao.cloud.userapi.aspect.FeignExceptionDeal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;

/**
 * 
 * @author wsfeng
 *
 */
@Slf4j
@Component
public class HttpUtil {

	private static ApplicationContext applicationContext;

	@Resource
	public void setApplicationContext(ApplicationContext applicationContext) {
		HttpUtil.applicationContext = applicationContext;
	}

	/**
	 * get请求查询
	 * @param url 请求路径
	 */
	public void doGet(String url) {
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse response = client.execute(get);
			String res = EntityUtils.toString(response.getEntity());
			System.out.println(res);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * post请求
	 * @param url 请求路径
	 * @param json 请求数据
	 * @param seconds 超时时长
	 * @return JSONObject
	 */

	public static String doPost(String url,String json,int seconds){
		DTOT dtot = DTOT.builder().data(json).url(url).seconds(seconds).build();
		try {
			HttpUtil bean = applicationContext.getBean(HttpUtil.class);
			return bean.post(dtot).getData();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return StringUtils.EMPTY;
	}

	@FeignExceptionDeal
	public BaseResponse<String> post(@RequestBody DTOT data) throws IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		int seconds = data.getSeconds()*1000;

		HttpPost post = new HttpPost(data.getUrl());

		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(seconds).setConnectionRequestTimeout(seconds).setSocketTimeout(seconds).build();
		post.setConfig(requestConfig);

		StringEntity s = new StringEntity(data.getData(),"UTF-8");
		s.setContentType("application/json");
		post.setEntity(s);
		HttpResponse res;
		res = client.execute(post);
		if(Objects.nonNull(res) && res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
			return BaseResponse.successData(EntityUtils.toString(res.getEntity()));
		}
		return BaseResponse.successData(StringUtils.EMPTY);
	}
}
