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
import java.util.ArrayList;
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
    public BaseResponse<String> createUser(@RequestParam String data) {
//        orderFacade.createUser(data);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ddddddddddddddddddddddd");
        return BaseResponse.successData("name111");
    }

    @PostMapping("/create1")
    public BaseResponse<String> getUser(@RequestBody String data) {
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
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("efsdfasef");
        return new ArrayList<>();
//        return orderFacade.batchQuery(list);
    }

    /**
     * 批次查询
     * @param wId wId
     * @return List<WaybillBcList>
     */
    @GetMapping("/singleQuery")
    public BaseResponse<WaybillBcList> singleQuery(@RequestParam("wId") String wId,@RequestParam("batchId") String batchId){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("eeeeeee");
        return BaseResponse.successData(new WaybillBcList());
    }
}
