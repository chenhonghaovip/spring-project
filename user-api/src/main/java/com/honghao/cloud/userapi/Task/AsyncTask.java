package com.honghao.cloud.userapi.Task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步操作
 *
 * @author chenhonghao
 * @date 2019-07-20 12:19
 */
@Slf4j
@Component
public class AsyncTask {

    @Async("asyncPool")
    public void sendInfo(){
        log.info("async start info");
    }
}
