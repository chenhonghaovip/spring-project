package com.honghao.cloud.accountapi;

import com.honghao.cloud.accountapi.common.dict.Dict;
import com.honghao.cloud.accountapi.domain.entity.ShopInfo;
import com.honghao.cloud.accountapi.service.RedisService;
import com.honghao.cloud.basic.common.base.factory.ThreadPoolFactory;
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
//    @Resource
//    private ElasticsearchTemplate elasticsearchTemplate;
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
//    https://www.cnblogs.com/h--d/p/12498224.html
//    https://www.cnblogs.com/lifengdi/archive/2019/09/20/11554923.html
    @Test
    public void test(){
        ShopInfo shopInfo = ShopInfo.builder().shopId("123").shopName("123").build();
//
//        IndexQuery indexQuery = new IndexQueryBuilder()
//                .withId(shopInfo.getShopId())
//                .withObject(shopInfo)
//                .build();
//
//        // 存入索引，返回文档ID
//        String documentId = elasticsearchTemplate.index(indexQuery);
//        System.out.println(documentId);
    }

    // 测试elasticsearchTemplate搜索
    @Test
    public void search() throws IOException {

        String json = "{\n" +
                "        \"match\" : {\n" +
                "            \"content\" : \"Hello\"\n" +
                "        }\n" +
                "    }";

//        StringQuery query = new StringQuery(json);
//        query.addIndices("test");
//        query.addTypes("news");
//
//        List<ShopInfo> articles = elasticsearchTemplate.queryForList(query, ShopInfo.class);
//        if(articles.size() > 0) {
//            for (ShopInfo a : articles){
//                System.out.println(a);
//            }
//        }
    }

}
