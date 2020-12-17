package com.honghao.cloud.userapi.listener.event;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author chenhonghao
 * @date 2019-07-22 21:48
 */
@Component
@Slf4j
public class EventDemoListener1 {

    @Async("asyncPool")
    @EventListener(classes = EventDemo.class)
    public void onApplicationEvent(EventDemo eventDemo) {
        log.info(JSON.toJSONString(eventDemo));
        log.info("事件监听1:{}", eventDemo);
    }
}
