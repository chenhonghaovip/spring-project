package com.honghao.cloud.userapi.listener.event;

import com.honghao.cloud.userapi.dto.request.EventDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 事件源
 *
 * @author chenhonghao
 * @date 2019-07-22 21:35
 */
@Getter
public class EventDemo extends ApplicationEvent {
    private EventDTO eventDTO;

    public EventDemo(Object source, EventDTO eventDTO) {
        super(source);
        this.eventDTO=eventDTO;
    }
}
