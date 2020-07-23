package com.honghao.cloud.userapi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.client.OrderClient;
import com.honghao.cloud.userapi.component.RedisService;
import com.honghao.cloud.userapi.domain.entity.ErrMsg;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import com.honghao.cloud.userapi.domain.mapper.master.ErrMsgMapper;
import com.honghao.cloud.userapi.facade.BatchFacade;
import com.honghao.cloud.userapi.facade.WaybillBcListFacade;
import com.honghao.cloud.userapi.factory.ExecutorFactory;
import com.honghao.cloud.userapi.listener.rabbitmq.producer.MessageSender;
import com.honghao.cloud.userapi.utils.BloomFilterHelper;
import com.honghao.cloud.userapi.utils.HttpUtil;
import com.honghao.cloud.userapi.utils.JedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.params.geo.GeoRadiusParam;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * java8
 * @author chenhonghao
 * @date 2019-11-28 15:23
 */
@Slf4j
@RestController
@RequestMapping("/testController")
public class TestController {
    private BloomFilterHelper<String> orderBloomFilterHelper = new BloomFilterHelper<>((Funnel<String>) (from, into) -> into.putString(from, Charsets.UTF_8)
            .putString(from, Charsets.UTF_8), 100 , 0.01);

    private ThreadPoolExecutor threadPoolExecutor = ExecutorFactory.buildThreadPoolExecutor(1000,1200,"test");
    @Resource
    private WaybillBcListFacade waybillBcListFacade;
    @Resource
    private BatchFacade batchFacade;
    @Resource
    private MessageSender messageSender;
    @Resource
    private OrderClient orderClient;
    @Resource
    private ErrMsgMapper errMsgMapper;
    @Resource
    private JedisOperator jedisOperator;
    @Resource
    private RedisService redisService;

    @PostMapping("/test/test")
    public BaseResponse test(@RequestBody WaybillBcList waybillBcList){

        orderClient.createUser(new JSONObject());

        orderClient.singleQuery("123","431");
        List<String> strings = Arrays.asList("1","2");
        String request = JSON.toJSONString(strings);
        String result = HttpUtil.doPost("http://10.16.14.38:8082/order/batchQuery", request, 1);
        System.out.println(result);
        return BaseResponse.success();
    }

    @GetMapping("/retryTest/retryTest")
    public BaseResponse retryTest(@RequestParam String date){
        List<ErrMsg> select = errMsgMapper.select();
        select.forEach(each->{
            errMsgMapper.deleteByPrimaryKey(each.getId());
            messageSender.publicQueueProcessing(each.getMsg(), "honghao_queue");
        });
        return BaseResponse.success();
    }
    /**
     * geoHash测试
     * @param data data
     * @return BaseResponse
     */
    @PostMapping("/test003")
    public BaseResponse test03(@RequestBody String data){
        System.out.println(data);
        //存放geo信息，存放到redis中为zset结构
        jedisOperator.geoadd("test02",Double.valueOf("121.3717178602589"),Double.valueOf("31.17087293836589"),"33333");
        jedisOperator.geoadd("test02",Double.valueOf("121.3716178602589"),Double.valueOf("31.17087293836589"),"44444");
        jedisOperator.geoadd("test02",Double.valueOf("121.3715178602589"),Double.valueOf("31.17087293836589"),"55555");
        jedisOperator.geoadd("test02",Double.valueOf("121.3719178602589"),Double.valueOf("31.17087293836589"),"11111");
        jedisOperator.geoadd("test02",Double.valueOf("121.3718178602589"),Double.valueOf("31.17087293836589"),"22222");

        //获取半径范围内的所有订单信息（主键值，经纬度，距离信息），并且按照顺序升序排序
        List<GeoRadiusResponse> list1 = jedisOperator.georadius("test02", Double.valueOf("121.3719178602589"), Double.valueOf("31.17087293836589"), 1, GeoUnit.M, GeoRadiusParam.geoRadiusParam().withCoord().withDist().sortDescending());
        for (GeoRadiusResponse geoRadiusResponse : list1) {
            geoRadiusResponse.getMemberByString();
            System.out.println( geoRadiusResponse.getMemberByString() + " distance:" + geoRadiusResponse.getDistance());
        }

        List<GeoRadiusResponse> result = list1.subList(0,Math.min(list1.size(),25));
        String wId;
        double lo,la,dist;
        for (GeoRadiusResponse geoRadiusResponse : result) {
            wId = geoRadiusResponse.getMemberByString();
            lo = geoRadiusResponse.getCoordinate().getLongitude();
            la = geoRadiusResponse.getCoordinate().getLongitude();
            dist = geoRadiusResponse.getDistance();
        }
        //获取这两个key值之间的距离
        Double dis = jedisOperator.geodist("test02","11111","22222",GeoUnit.M);
        System.out.println(dis.doubleValue());


        return BaseResponse.success();
    }

    /**
     * 多数据源事务处理
     * @return BaseResponse
     */
    @GetMapping("/dataSourceTest")
    public BaseResponse orderList(){
        return waybillBcListFacade.dateSource();
    }

    /**
     * 请求合并
     */
    @GetMapping("/selectInfo")
    public void select(){
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            threadPoolExecutor.execute(() -> {
                try {
                    countDownLatch.await();
                    BaseResponse<WaybillBcList> response = batchFacade.queryCommon1(String.valueOf(finalI));
                    if (response.isResult() && response.getData().getWId().equals("874")){
                        log.info("请求参数为{},结果为：{}",finalI,response.getData());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        countDownLatch.countDown();
    }

    /**
     * 1.包裹TtlExecutors
     *
     * 2.使用TransmittableThreadLocal
     */
    @Test
    public void testInfo(){
        TransmittableThreadLocal<Integer> ttl = new TransmittableThreadLocal<>();
        ThreadPoolExecutor threadPool = ExecutorFactory.buildThreadPoolExecutor(1,1,"odoofd");
        ExecutorService ttlExecutorService = TtlExecutors.getTtlExecutorService(threadPool);
        ttl.set(1);
        ttlExecutorService.submit(()->{
            System.out.println("第一次"+Thread.currentThread().getName()+"value:"+ttl.get());
            ttl.remove();
        });

        ttl.set(3);

        ttlExecutorService.submit(()->{
            System.out.println("第二次"+Thread.currentThread().getName()+"value:"+ttl.get());
        });
    }

    /**
     * redis实现布隆过滤器功能
     * @return BaseResponse
     */
    @GetMapping("/getList")
    public BaseResponse getList(){
        String batchId = "2020012548548627";
        redisService.includeByBloomFilter(orderBloomFilterHelper, "order", batchId);
        redisService.addByBloomFilter(orderBloomFilterHelper, "order", batchId);
        return BaseResponse.success();
    }
}
