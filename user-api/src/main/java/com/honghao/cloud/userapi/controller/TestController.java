package com.honghao.cloud.userapi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.client.OrderClient;
import com.honghao.cloud.userapi.domain.entity.ErrMsg;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import com.honghao.cloud.userapi.domain.mapper.master.ErrMsgMapper;
import com.honghao.cloud.userapi.facade.BatchFacade;
import com.honghao.cloud.userapi.facade.WaybillBcListFacade;
import com.honghao.cloud.userapi.factory.ExecutorFactory;
import com.honghao.cloud.userapi.listener.rabbitmq.producer.MessageSender;
import com.honghao.cloud.userapi.utils.HttpUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.bind.annotation.*;

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
    private ThreadPoolExecutor threadPoolExecutor = ExecutorFactory.buildThreadPoolExecutor(2,4,"test");
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

    @PostMapping("/test/test")
    @GlobalTransactional
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
     * 多数据源事务处理
     * @return BaseResponse
     */
    @GetMapping("/dataSourceTest")
    public BaseResponse orderList(){
        return waybillBcListFacade.dateSource();
    }

    @GetMapping("/reflexTest")
    public BaseResponse reflexTest(){
        return waybillBcListFacade.reflexTest();
    }

    /**
     * 请求合并
     */
    @GetMapping("/selectInfo")
    public void select(){
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            threadPoolExecutor.execute(() -> {
                try {
                    countDownLatch.await();
                    BaseResponse<WaybillBcList> response = batchFacade.queryCommon(String.valueOf(finalI));
                    System.out.println("请求结果为"+response.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            countDownLatch.countDown();
        }
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
}
