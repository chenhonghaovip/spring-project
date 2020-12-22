package com.honghao.cloud.userapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.basic.common.bean.CacheTemplate;
import com.honghao.cloud.basic.common.factory.ThreadPoolFactory;
import com.honghao.cloud.userapi.client.OrderClient;
import com.honghao.cloud.userapi.domain.entity.ErrMsg;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import com.honghao.cloud.userapi.domain.mapper.master.ErrMsgMapper;
import com.honghao.cloud.userapi.facade.BatchFacade;
import com.honghao.cloud.userapi.facade.WaybillBcListFacade;
import com.honghao.cloud.userapi.listener.rabbitmq.producer.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.ref.SoftReference;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * java8
 *
 * @author chenhonghao
 * @date 2019-11-28 15:23
 */
@Slf4j
@RestController
@RequestMapping("/testController")
public class TestController {
    private ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(1000, 1200, "test");
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
    private CacheTemplate<ErrMsg> cacheTemplate;

    /**
     * 软引用何时被收集
     * 运行参数 -Xmx200m -XX:+PrintGC
     * Created by ccr at 2018/7/14.
     */
    public static void main(String[] args) {
        //100M的缓存数据
        byte[] cacheData = new byte[100 * 1024 * 1024];
        //将缓存数据用软引用持有
        SoftReference<byte[]> cacheRef = new SoftReference<>(cacheData);
        //将缓存数据的强引用去除
        cacheData = null;
        System.out.println("第一次GC前" + cacheData);
        System.out.println("第一次GC前" + cacheRef.get());
        //进行一次GC后查看对象的回收情况
        System.gc();
        //等待GC
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("第一次GC后" + cacheData);
        System.out.println("第一次GC后" + cacheRef.get());

        //在分配一个120M的对象，看看缓存对象的回收情况
        byte[] newData = new byte[120 * 1024 * 1024];
        System.out.println("分配后" + cacheData);
        System.out.println("分配后" + cacheRef.get());
    }

    @PostMapping("/test/test")
    public BaseResponse test(@RequestBody WaybillBcList waybillBcList) {
        System.out.println(waybillBcList);
        orderClient.createUser(new JSONObject());

        orderClient.singleQuery("123", "431");
        return BaseResponse.success();
    }

    @GetMapping("/retryTest/retryTest")
    public BaseResponse retryTest(@RequestParam String date) {
        System.out.println(date);
        List<ErrMsg> select = errMsgMapper.select();
        select.forEach(each -> {
            errMsgMapper.deleteByPrimaryKey(each.getId());
            messageSender.publicQueueProcessing(each.getMsg(), "honghao_queue");
        });
        return BaseResponse.success();
    }

    /**
     * 多数据源事务处理
     *
     * @return BaseResponse
     */
    @GetMapping("/dataSourceTest")
    public BaseResponse orderList() {
        return waybillBcListFacade.dateSource();
    }

    /**
     * 请求合并
     */
    @GetMapping("/selectInfo")
    public void select() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            threadPoolExecutor.execute(() -> {
                try {
                    countDownLatch.await();
                    BaseResponse<WaybillBcList> response = batchFacade.queryCommon1(String.valueOf(finalI));
                    if (response.isResult() && "874".equals(response.getData().getWId())) {
                        log.info("请求参数为{},结果为：{}", finalI, response.getData());
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
     * <p>
     * 2.使用TransmittableThreadLocal
     */
    @Test
    public void testInfo() {
        TransmittableThreadLocal<Integer> ttl = new TransmittableThreadLocal<>();
        ThreadPoolExecutor threadPool = ThreadPoolFactory.buildThreadPoolExecutor(1, 1, "odoofd");
        ExecutorService ttlExecutorService = TtlExecutors.getTtlExecutorService(threadPool);
        ttl.set(1);
        ttlExecutorService.submit(() -> {
            System.out.println("第一次" + Thread.currentThread().getName() + "value:" + ttl.get());
            ttl.remove();
        });

        ttl.set(3);

        ttlExecutorService.submit(() -> System.out.println("第二次" + Thread.currentThread().getName() + "value:" + ttl.get()));
    }

    /**
     * redis实现布隆过滤器功能
     *
     * @return BaseResponse
     */
    @GetMapping("/getList")
    public BaseResponse getList() {
        String batchId = "2020012548548627";
        return cacheTemplate.redisStringCache("order", batchId, () -> errMsgMapper.selectByPrimaryKey(123445L));
    }
}
