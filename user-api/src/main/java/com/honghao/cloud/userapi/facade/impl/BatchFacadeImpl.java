package com.honghao.cloud.userapi.facade.impl;

import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.userapi.client.OrderClient;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import com.honghao.cloud.userapi.facade.BatchFacade;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.Collectors;

/**
 * @author chenhonghao
 * @date 2020-02-14 21:11
 */
@Slf4j
@Service
public class BatchFacadeImpl implements BatchFacade {

    private LinkedBlockingQueue<Request> queues = new LinkedBlockingQueue<>();

    private LinkedBlockingQueue<Request> queues1 = new LinkedBlockingQueue<>();


    @Resource
    private OrderClient orderClient;
    @Data
    private class Request{
        private String movieCode;

        private CompletableFuture<WaybillBcList> future;

        private Thread thread;

        private WaybillBcList waybillBcList;
    }

    @PostConstruct
    public void init(){
//        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1, t-> new Thread(""+ATOMIC_INTEGER.getAndIncrement()));
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
//            List<Request> list = new ArrayList<>();
//            if (queues.size()==0) {
//                return;
//            }
//            for (int i =0;i<queues.size() ;i++) {
//                Request request1 = queues.poll();
//                list.add(request1);
//            }
//
//
//            List<String> data = list.stream().map(Request::getMovieCode).collect(Collectors.toList());
//            //批量请求
//            List<WaybillBcList> responses = orderClient.batchQuery(data);
//            log.info("合并请求数量为：{}，参数为：{}，结果为：{}",list.size(),data,responses);
//            //获取返回结果
//            Map<String,WaybillBcList> resultMap = new HashMap<>(responses.size()*2);
//            responses.forEach(each->resultMap.put(each.getWId(),each));
//
//
//            for (Request request : list) {
//                request.getFuture().complete(resultMap.get(request.getMovieCode()));
//            }

            try {
                if (queues1.size()==0){
                    return;
                }
                List<Request> list1 = new ArrayList<>();
                for (int i = 0; i < queues1.size(); i++) {
                    list1.add(queues1.poll());
                }
                List<String> data1 = list1.stream().map(Request::getMovieCode).collect(Collectors.toList());
                //批量请求
                List<WaybillBcList> responses1 = orderClient.batchQuery(data1);
                log.info("合并请求数量为：{}，参数为：{}，结果为：{}",list1.size(),data1,responses1);
                Map<String,WaybillBcList> resultMap1 = new HashMap<>(list1.size()*2);
                responses1.forEach(each -> resultMap1.put(each.getWId(), each));
                for (Request request : list1) {
                    request.setWaybillBcList(resultMap1.get(request.getMovieCode()));
                    LockSupport.unpark(request.getThread());
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }

        },0,100, TimeUnit.MILLISECONDS);
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


    @Override
    public BaseResponse<WaybillBcList> queryCommon1(String data) {
        Request request = new Request();
        request.setMovieCode(data);
        request.setThread(Thread.currentThread());
        queues1.add(request);
        LockSupport.park();
        return BaseResponse.successData(request.getWaybillBcList());
    }


}
