package com.honghao.cloud.userapi.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 设置要监听的队列
 *
 * @author chenhonghao
 * @date 2019-07-31 15:31
 */
@Data
@Slf4j
@Component
@ConfigurationProperties(prefix = "rabbit.config")
public class RabbitExchangeConfig {
    private String queues;
    private String[] args;

    @PostConstruct
    String[] initInfo(){
        JSONArray jsonArray= JSON.parseArray(queues);
        List<JSONObject> list = jsonArray.toJavaList(JSONObject.class);

        List<String> values = list.stream().map(each->each.getString("queue")).collect(Collectors.toList());
        args= values.toArray(new String[0]);
        return args;
    }
}
