package com.honghao.cloud.userapi.facade.impl;

import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.client.OrderClient;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import com.honghao.cloud.userapi.facade.BatchFacade;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author chenhonghao
 * @date 2020-02-14 21:11
 */
@Slf4j
@Service
public class BatchFacadeImpl implements BatchFacade {
    private static final int max = 1000;

    private LinkedBlockingQueue<Request> queues = new LinkedBlockingQueue<>();

    @Resource
    private OrderClient orderClient;
    @Data
    private class Request{
        private String movieCode;

        private CompletableFuture<WaybillBcList> future;
    }

    @PostConstruct
    public void init(){
//        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            List<Request> list = new ArrayList<>();
            if (queues.size()==0) {
                return;
            }
            for (int i =0;i<queues.size() ;i++) {
                Request request1 = queues.poll();
                list.add(request1);
            }


            List<String> data = list.stream().map(Request::getMovieCode).collect(Collectors.toList());
            //批量请求
            List<WaybillBcList> responses = orderClient.batchQuery(data);
            log.info("合并请求数量为：{}，参数为：{}，结果为：{}",list.size(),data,responses);
            //获取返回结果
            Map<String,WaybillBcList> resultMap = new HashMap<>(responses.size()*2);
            responses.forEach(each->resultMap.put(each.getWId(),each));

            for (Request request : list) {
                request.getFuture().complete(resultMap.get(request.getMovieCode()));
            }

        },0,10, TimeUnit.MILLISECONDS);
    }


    @Override
    public BaseResponse<WaybillBcList> queryCommon(String data) throws Exception{
        Request request = new Request();
        request.setMovieCode(data);
        CompletableFuture<WaybillBcList> future = new CompletableFuture<>();
        request.setFuture(future);
        queues.add(request);
        return BaseResponse.successData(future.get());
    }


    @Test
    public void getIn(){
        List<String> list = new ArrayList<>();
        list.add("11");
        list.add("22");
        list.remove("11");


        Map<String,String> map = new HashMap<>();
        map.put("1","1");
        map.remove("1");
        System.out.println(map);
    }
}
