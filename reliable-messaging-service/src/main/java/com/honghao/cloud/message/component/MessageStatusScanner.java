package com.honghao.cloud.message.component;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.factory.ThreadPoolFactory;
import com.honghao.cloud.basic.common.base.utils.HttpUtil;
import com.honghao.cloud.message.common.enums.MsgStatusEnum;
import com.honghao.cloud.message.domain.entity.MsgInfo;
import com.honghao.cloud.message.domain.mapper.MsgInfoMapper;
import com.honghao.cloud.message.dto.MsgDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
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
@Slf4j
@Component
public class MessageStatusScanner {
    private static final String KEY = "MessageStatusScanner";
    private static final ScheduledThreadPoolExecutor SCHEDULED_THREAD_POOL_EXECUTOR = ThreadPoolFactory.buildScheduledThreadPoolExecutor(1,"timeScanner");
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = ThreadPoolFactory.buildThreadPoolExecutor(10,25,1000,"messageDeal");
    @Resource
    private MsgInfoMapper msgInfoMapper;
    @Resource
    private MessageSender messageSender;
    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    public MessageStatusScanner() {
        SCHEDULED_THREAD_POOL_EXECUTOR.scheduleAtFixedRate(this::messageStatusScanner,10,10, TimeUnit.SECONDS);
    }

    /**
     as* 定时任务扫描消息查询消息状态
     */
    private void messageStatusScanner(){
        // 加锁防止集群环境下的定时任务一起执行，每次只需有一个服务可以执行即可
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(KEY, UUID.randomUUID(), 9, TimeUnit.SECONDS);

        if (aBoolean!=null && aBoolean){
            // 查询消息表中全部的未完成数据
            List<MsgInfo> collect = msgInfoMapper.selectByStatus();
            LocalDateTime now = LocalDateTime.now();

            // 过滤一个时间间隔内的数据不去回调查询，避免刚发送消息后，业务还没来得及处理就被发起查询请求，导致以为业务处理失败而导致消息丢失的情况
            List<MsgInfo> list = collect.stream()
                    .filter(each -> !now.minusSeconds(each.getDelay()).isBefore(each.getCreateTime()) && each.getRetryTime() <5)
                    .collect(Collectors.toList());

            // TODO: 2020/9/24 可以异步处理
            // 默认5次回调查询失败后，发出报警信息，人工干预处理
            collect.stream().filter(each->each.getRetryTime()>=5).collect(Collectors.toList()).forEach(k-> log.error("重试超时:{}",JSON.toJSONString(k)));

            if (CollectionUtils.isNotEmpty(list)){
                // 根据消息状态分类，不同消息状态的消息使用不同的处理策略
                Map<Integer, List<MsgInfo>> map = list.stream().collect(Collectors.groupingBy(MsgInfo::getStatus));

                // 消息状态为0的需要向发起方发起查询
                List<MsgInfo> infos = map.get(MsgStatusEnum.TO_BE_CONFIRMED.getCode());
                if (Objects.nonNull(infos)){
                    // 通过对业务方AppId + url 进行分类，以方便使用线程池进行批次查询
                    Map<String, List<MsgInfo>> temp = infos.stream().collect(Collectors.groupingBy(m -> m.getAppId() + m.getUrl()));

                    // 通过对接口请求路径用不同线程处理来缩短时间
                    for (Map.Entry<String, List<MsgInfo>> entry : temp.entrySet()) {
                        THREAD_POOL_EXECUTOR.execute(()->{
                            MsgInfo msgInfo = entry.getValue().get(0);
                            String appId = msgInfo.getAppId();
                            String url = msgInfo.getUrl();
                            // 封装同一个接口的请求参数
                            retry(entry.getValue(),appId,url,MsgStatusEnum.HAS_BEEN_SENT.getCode());
                        });
                    }
                }

                // 消息状态为1的直接重新发送到消息队列，由消费方实现幂等性处理
                THREAD_POOL_EXECUTOR.execute(()-> {
                    List<MsgInfo> info = map.get(MsgStatusEnum.HAS_BEEN_SENT.getCode());
                    if (Objects.nonNull(info)){
                        info.forEach(each->{
                            // 通过对消费方AppId + url 进行分类，以方便进行批次查询
                            messageSender.publicQueueProcessing(JSON.toJSONString(each),each.getTopic());
                        });
                    }
                });
            }
        }
    }

    /**
     * 通过切换ip请求的方式做重试处理
     * @param entry collect
     */
    private void retry(List<MsgInfo> entry,String appId,String url,int msgStatus){
        // 获取业务方给予的业务id,通过此id向生产者和消费者进行查询操作
        List<MsgDTO> collect = entry.stream().map(each -> MsgDTO.builder().businessId(each.getBusinessId()).msgId(each.getMsgId()).build()).collect(Collectors.toList());

        // 获取注册中心的回调app服务的ip地址集合
        List<ServiceInstance> instances = discoveryClient.getInstances(appId);
        for (ServiceInstance instance : instances) {
            url = instance.getUri() + url;

            // 只把处理完成的消息的id返回给我
            BaseResponse<List<Long>> result = HttpUtil.doPost(url, JSON.toJSONString(collect), 2);
            List<Long> data = result.getData();

            if (result.isResult() && Objects.nonNull(data)){
                // 把处理完成的消息修改为已发送，并且发送消息到队列中
                if (CollectionUtils.isNotEmpty(data)){
                    msgInfoMapper.updateBatch(data,msgStatus);
                }

                // 选择所有成功的消息，发送队列消费
                List<MsgInfo> sendList = entry.stream().filter(l -> data.contains(l.getMsgId())).collect(Collectors.toList());
                sendList.forEach(k->messageSender.publicQueueProcessing(JSON.toJSONString(k),k.getTopic()));

                // 处理失败的删除对应的消息
                entry.removeAll(sendList);
                if (CollectionUtils.isNotEmpty(entry)){
                    List<Long> msgIds = entry.stream().map(MsgInfo::getMsgId).collect(Collectors.toList());
                    msgInfoMapper.deleteBatch(msgIds);
                }
                return;
            }
        }
        // 轮询之后如果还没有成功，则重试次数+1
        List<Long> msgIds = entry.stream().map(MsgInfo::getMsgId).collect(Collectors.toList());
        msgInfoMapper.updateRetryTimes(msgIds);
    }
}
