package com.honghao.cloud.message.component;

import com.alibaba.fastjson.JSONObject;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.factory.ThreadPoolFactory;
import com.honghao.cloud.basic.common.base.utils.HttpUtil;
import com.honghao.cloud.message.common.enums.MsgStatusEnum;
import com.honghao.cloud.message.domain.entity.MsgInfo;
import com.honghao.cloud.message.domain.mapper.MsgInfoMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 消息状态扫描查询
 *
 * @author chenhonghao
 * @date 2020-09-04 17:01
 */
@Component
public class MessageStatusScanner {
    private static final ScheduledThreadPoolExecutor SCHEDULED_THREAD_POOL_EXECUTOR = ThreadPoolFactory.buildScheduledThreadPoolExecutor(1);
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = ThreadPoolFactory.buildThreadPoolExecutor(5,25,1000,"messageDeal");
    @Resource
    private MsgInfoMapper msgInfoMapper;
    @Resource
    private MessageSender messageSender;
    @Resource
    private DiscoveryClient discoveryClient;

    public MessageStatusScanner() {
        SCHEDULED_THREAD_POOL_EXECUTOR.scheduleAtFixedRate(this::messageStatusScanner,10,60, TimeUnit.SECONDS);
    }

    /**
     * 定时任务扫描消息查询消息状态
     */
    private void messageStatusScanner(){
        // 查询消息表中全部的未完成数据
        List<MsgInfo> list = msgInfoMapper.selectByStatus();
        if (CollectionUtils.isNotEmpty(list)){
            Map<Integer, List<MsgInfo>> map = list.stream().collect(Collectors.groupingBy(MsgInfo::getStatus));

            // 消息状态为0的需要向发起方发起查询
            map.get(MsgStatusEnum.TO_BE_CONFIRMED.getCode()).forEach(each->{
                Map<String, List<MsgInfo>> temp = list.stream().collect(Collectors.groupingBy(m -> m.getAppId() + m.getUrl()));

                for (Map.Entry<String, List<MsgInfo>> entry : temp.entrySet()) {
                    MsgInfo msgInfo = entry.getValue().get(0);
                    String appId = msgInfo.getAppId();
                    String url = msgInfo.getUrl();
                    // 封装同一个接口的请求参数
                    List<MsgInfo> retry = retry(entry.getValue(), appId, url, MsgStatusEnum.TO_BE_SENT.getCode());
                    retry.forEach(k->messageSender.publicQueueProcessing(k,k.getTopic()));
                }
            });

            // 消息状态为1的需要重新发送消息队列
            map.get(MsgStatusEnum.TO_BE_SENT.getCode())
                    .forEach(each->messageSender.publicQueueProcessing(each,each.getTopic()));

            // 消息状态为2的需要向业务消费方查询
            map.get(MsgStatusEnum.HAS_BEEN_SENT.getCode()).forEach(each->{
                Map<String, List<MsgInfo>> temp = list.stream().collect(Collectors.groupingBy(m -> m.getAppId() + m.getUrl()));

                for (Map.Entry<String, List<MsgInfo>> entry : temp.entrySet()) {
                    MsgInfo msgInfo = entry.getValue().get(0);
                    String appId = msgInfo.getConsumerAppId();
                    String url = msgInfo.getConsumerUrl();
                    // 封装同一个接口的请求参数
                    retry(entry.getValue(),appId,url,MsgStatusEnum.COMPLETED.getCode());
                }
            });


            // 通过对AppId + url 进行分类，以方便进行批次查询

        }
    }

    /**
     * 通过切换ip请求的方式做重试处理
     * @param entry collect
     */
    private List<MsgInfo> retry(List<MsgInfo> entry,String appId,String url,int msgStatus){
        List<String> collect = entry.stream().map(MsgInfo::getBusinessId).collect(Collectors.toList());
        // 获取注册中心的回调app服务的ip地址集合
        List<ServiceInstance> instances = discoveryClient.getInstances(appId);
        for (ServiceInstance instance : instances) {
            url = instance.getUri() + url;
            JSONObject json = new JSONObject();
            json.put("keys",collect);

            // 只把处理完成的消息的id返回给我
            BaseResponse<List<String>> result = HttpUtil.doPost(url, json.toJSONString(), 2);
            List<String> data = result.getData();

            if (result.isResult() && Objects.nonNull(data)){
                List<MsgInfo> successList = entry.stream().filter(each -> data.contains(each.getBusinessId())).collect(Collectors.toList());
                // do something 修改数据库中消息的状态
                List<Long> msgIds = successList.stream().map(MsgInfo::getMsgId).collect(Collectors.toList());

                msgInfoMapper.updateBatch(msgIds,msgStatus);
                return successList;
            }
        }
        return Collections.emptyList();
    }

    public static void main(String[] args) {
        String url = "http://localhost:8084/eurekaController/test";
        JSONObject json = new JSONObject();
        json.put("keys", Arrays.asList("1","2"));
        BaseResponse result = HttpUtil.doPost(url, json.toJSONString(), 5);
    }
}
