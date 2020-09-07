package com.honghao.cloud.message.component;

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
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    private static final ScheduledThreadPoolExecutor SCHEDULED_THREAD_POOL_EXECUTOR = ThreadPoolFactory.buildScheduledThreadPoolExecutor(1,"timeScanner");
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
            THREAD_POOL_EXECUTOR.execute(()-> {
                List<MsgInfo> infos = map.get(MsgStatusEnum.TO_BE_CONFIRMED.getCode());
                if (Objects.nonNull(infos)){
                    infos.forEach(each->{
                        // 通过对业务方AppId + url 进行分类，以方便进行批次查询
                        Map<String, List<MsgInfo>> temp = list.stream().collect(Collectors.groupingBy(m -> m.getAppId() + m.getUrl()));

                        for (Map.Entry<String, List<MsgInfo>> entry : temp.entrySet()) {
                            MsgInfo msgInfo = entry.getValue().get(0);
                            String appId = msgInfo.getAppId();
                            String url = msgInfo.getUrl();
                            // 封装同一个接口的请求参数
                            retry(entry.getValue(),appId,url,MsgStatusEnum.HAS_BEEN_SENT.getCode());
                        }
                    });
                }
            });

            // 消息状态为1的需要向业务消费方查询
            THREAD_POOL_EXECUTOR.execute(()-> {
                List<MsgInfo> infos = map.get(MsgStatusEnum.HAS_BEEN_SENT.getCode());
                if (Objects.nonNull(infos)){
                   infos.forEach(each->{
                        // 通过对消费方AppId + url 进行分类，以方便进行批次查询
                        Map<String, List<MsgInfo>> temp = list.stream().collect(Collectors.groupingBy(m -> m.getConsumerAppId() + m.getConsumerUrl()));
                        for (Map.Entry<String, List<MsgInfo>> entry : temp.entrySet()) {
                            MsgInfo msgInfo = entry.getValue().get(0);
                            String appId = msgInfo.getConsumerAppId();
                            String url = msgInfo.getConsumerUrl();
                            // 封装同一个接口的请求参数
                            retry(entry.getValue(),appId,url,MsgStatusEnum.COMPLETED.getCode());
                        }
                    });
                }
            });
        }
    }

    /**
     * 通过切换ip请求的方式做重试处理
     * @param entry collect
     */
    private void retry(List<MsgInfo> entry,String appId,String url,int msgStatus){
        // 获取业务方给予的业务id,通过此id向生产者和消费者进行查询操作
        List<Long> collect = entry.stream().map(MsgInfo::getMsgId).collect(Collectors.toList());

        // 获取注册中心的回调app服务的ip地址集合
        List<ServiceInstance> instances = discoveryClient.getInstances(appId);
        for (ServiceInstance instance : instances) {
            url = instance.getUri() + url;

            // 只把处理完成的消息的id返回给我
            BaseResponse<List<Long>> result = HttpUtil.doPost(url, collect.toString(), 2);
            List<Long> data = result.getData();

            if (result.isResult() && Objects.nonNull(data)){

                msgInfoMapper.updateBatch(data,msgStatus);
                List<MsgInfo> list1 = entry.stream().filter(l -> data.contains(l.getMsgId())).collect(Collectors.toList());
                collect.removeAll(data);

                // 当修改状态为1时，说明时向业务发起方查询
                if (msgStatus == 1){
                    // do something 修改数据库中消息的状态
                    list1.forEach(k->messageSender.publicQueueProcessing(k,k.getTopic()));
                    // 失败的删除

                    if (CollectionUtils.isNotEmpty(collect)){
                        msgInfoMapper.deleteBatch(collect);
                    }
                    return;
                }else {
                    // 查询失败的消息，重新投递队列
                    List<MsgInfo> collect1 = entry.stream().filter(k -> collect.contains(k.getMsgId())).collect(Collectors.toList());
                    // 失败的重新投递队列
                    collect1.forEach(k->messageSender.publicQueueProcessing(k,k.getTopic()));
                    return;
                }
            }
        }
    }
}
