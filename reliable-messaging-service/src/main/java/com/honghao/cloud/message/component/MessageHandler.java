package com.honghao.cloud.message.component;

import com.honghao.cloud.message.domain.entity.MsgInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.handler.EntryHandler;

/**
 * @author chenhonghao
 * @date 2020-09-20 23:05
 */
@Slf4j
@Component
public class MessageHandler implements EntryHandler<MsgInfo> {
    @Override
    public void insert(MsgInfo msgInfo) {
        System.out.println(msgInfo);
    }

    @Override
    public void update(MsgInfo before, MsgInfo after) {
        System.out.println(before+""+after);
    }

    @Override
    public void delete(MsgInfo msgInfo) {
        System.out.println(msgInfo);
    }
}
