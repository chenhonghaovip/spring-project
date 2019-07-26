package com.honghao.cloud.userapi;

import com.honghao.cloud.userapi.listener.rabbitmq.producer.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * MVC形式调用
 *
 * @author chenhonghao
 * @date 2019-07-26 09:45
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApiApplication.class)
public class MvcTest {
    @Resource
    private MessageSender messageSender;
    @Test
    public void test02(){
        log.info("开始接口测试工作");
        messageSender.pushInfoUser("name is chenhonghao");
    }
}
