package com.honghao.cloud.accountapi;

import com.honghao.cloud.accountapi.common.dict.Dict;
import com.honghao.cloud.accountapi.service.RedisService;
import com.honghao.cloud.basic.common.base.factory.ThreadPoolFactory;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountApiApplication.class)
public class AccountApiApplicationTests {
    private Random random = new Random();
    @Resource
    private RedisService redisService;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private RestHighLevelClient restHighLevelClient;
    @Test
    public void contextLoads() {
        // 初始化构建过去30天的数据
        long hour = System.currentTimeMillis()/(1000*60*60);
        for (int i = 1; i < 24*30; i++) {
            String testkey = Dict.WEIBO;
            testkey +=(hour - i);
            for (int j = 1; j <= 26; j++) {
                redisTemplate.opsForZSet().add(testkey,String.valueOf((char)(96+j)),random.nextInt(10));
            }
        }
    }

    @Test
    public void redisTest(){
        for (int i = 0; i < 1000; i++) {
            int i1 = random.nextInt(26);
            String key = String.valueOf((char)(97+i1));
            redisService.hotSearchOnWeibo(key);
        }
    }

    @Test
    public void timeTask(){
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = ThreadPoolFactory.buildScheduledThreadPoolExecutor(1);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(()->{
            // 刷新过去24小时的数据到日热点数据
            long hour = System.currentTimeMillis()/(1000*60*60);

            String key = Dict.WEIBO_WEEK + hour;


        },0,2, TimeUnit.SECONDS);
    }

    @Test
    public void elas() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("account_api");
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);

    }

}
