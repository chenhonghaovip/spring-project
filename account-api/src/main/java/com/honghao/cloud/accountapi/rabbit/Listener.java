package com.honghao.cloud.accountapi.rabbit;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.accountapi.client.MessageClient;
import com.honghao.cloud.accountapi.config.RabbitConfig;
import com.honghao.cloud.accountapi.dto.request.MsgInfoDTO;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author chenhonghao
 * @date 2020-09-24 10:36
 */
@Slf4j
@Component
public class Listener {
    @Resource
    private MessageClient messageClient;
    @Resource
    private RestHighLevelClient restHighLevelClient;

    @RabbitListener(queues = RabbitConfig.CREATE_ORDER)
    public void process(String data) {
        try {
            log.info("订单异步双写elasticsearch队列消息为：{}",data);
            MsgInfoDTO msgInfoDTO = JSON.parseObject(data, MsgInfoDTO.class);
            IndexRequest indexRequest = new IndexRequest(RabbitConfig.CREATE_ORDER);
            indexRequest.id(msgInfoDTO.getBusinessId());
            indexRequest.source(msgInfoDTO.getContent(), XContentType.JSON);
            IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            if (Objects.equals(index.status(), RestStatus.OK) || Objects.equals(index.status(), RestStatus.CREATED)){
                BaseResponse complete = messageClient.complete(msgInfoDTO);
                if (!complete.isResult()){
                    log.error(complete.getRemark());
                }
                log.info("订单异步双写elasticsearch成功，接受信息成功");
            }
        }catch (Exception e){
            log.info("消息队列发送操作异常:{}",e.getMessage());
        }
    }
}
