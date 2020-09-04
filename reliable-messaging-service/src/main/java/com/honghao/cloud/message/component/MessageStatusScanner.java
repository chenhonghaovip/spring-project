package com.honghao.cloud.message.component;

import com.alibaba.fastjson.JSONObject;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.basic.common.base.factory.ThreadPoolFactory;
import com.honghao.cloud.basic.common.base.utils.HttpUtil;
import com.honghao.cloud.message.domain.entity.MsgInfo;
import com.honghao.cloud.message.domain.mapper.MsgInfoMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
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
            // 通过对AppId + url 进行分类，以方便进行批次查询
            Map<String, List<MsgInfo>> map = list.stream().collect(Collectors.groupingBy(each -> each.getAppId() + each.getUrl()));
            for (Map.Entry<String, List<MsgInfo>> entry : map.entrySet()) {
                MsgInfo msgInfo = entry.getValue().get(0);

                // 封装同一个接口的请求参数
                List<String> collect = entry.getValue().stream().map(MsgInfo::getTopic).collect(Collectors.toList());
                retry(msgInfo,collect);
            }
        }
    }

    /**
     * 通过切换ip请求的方式做重试处理
     * @param msgInfo msgInfo
     * @param collect collect
     */
    private void retry(MsgInfo msgInfo, List<String> collect){
        // 获取注册中心的回调app服务的ip地址集合
        List<ServiceInstance> instances = discoveryClient.getInstances(msgInfo.getAppId());
        for (ServiceInstance instance : instances) {
            String url = instance.getUri()+msgInfo.getUrl();
            JSONObject json = new JSONObject();
            json.put("keys",collect);
            // 只把处理完成的消息的id返回给我
            BaseResponse result = HttpUtil.doPost(url, json.toJSONString(), 2);
            if (result.isResult()){
                // do something 修改数据库中消息的状态
                return;
            }
        }
    }
}
