package com.honghao.cloud.orderapi.controller;

import com.honghao.cloud.orderapi.base.BaseResponse;
import com.honghao.cloud.orderapi.domain.entity.WaybillBcList;
import com.honghao.cloud.orderapi.facade.OrderFacade;
import com.honghao.cloud.orderapi.listener.rabbitmq.producer.MessageSender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户信息controller
 *
 * @author chenhonghao
 * @date 2019-07-17 17:22
 */
@Slf4j
@RestController
@RequestMapping("/order")
@Api("用户接口服务")
public class OrderController {
    @Resource
    private OrderFacade orderFacade;
    @Resource
    private MessageSender messageSender;

    @PostMapping("/create")
    @ApiOperation(value = "创建订单",notes = "创建订单")
    BaseResponse<String> createUser(@RequestParam String data) {
        orderFacade.createUser(data);
        return BaseResponse.successData("name111");
    }

    @PostMapping("/create1")
    BaseResponse<String> getUser(@RequestBody String data) {
//        CardDTO cardDTO=new CardDTO();
        messageSender.pushInfoUser("chenhonghao");
//        CardDTO.AccountInfoBean accountInfoBean=new CardDTO.AccountInfoBean();
        return null;
    }

    /**
     * 批次查询
     * @param list list
     * @return List<WaybillBcList>
     */
    @PostMapping("/batchQuery")
    public List<WaybillBcList> batchQuery(@RequestBody List<String> list){
        return orderFacade.batchQuery(list);
    }
}
