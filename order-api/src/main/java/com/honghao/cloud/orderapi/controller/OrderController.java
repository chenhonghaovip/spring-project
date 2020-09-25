package com.honghao.cloud.orderapi.controller;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.orderapi.domain.entity.WaybillBcList;
import com.honghao.cloud.orderapi.dto.request.MsgDTO;
import com.honghao.cloud.orderapi.facade.OrderFacade;
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

    @PostMapping("/create")
    @ApiOperation(value = "创建订单",notes = "创建订单")
    public BaseResponse createOrder(@RequestParam String data) {
        return orderFacade.createOrders(data);
    }


    @PostMapping("/createBatchOrder")
    @ApiOperation(value = "创建订单",notes = "创建订单")
    public BaseResponse createBatchOrder(@RequestParam String data) {
        return orderFacade.createBatchOrder(data);
    }


    /**
     * 批次查询 - 通过查询订单是否生成返回消息id
     * @param data list
     * @return List<WaybillBcList>
     */
    @PostMapping("/batchQuery")
    @ApiOperation(value = "批次查询",notes = "批次查询")
    public BaseResponse batchQuery(@RequestBody String data){
//        if (true){
//            throw new RuntimeException("111");
//        }
        List<MsgDTO> list = JSON.parseArray(data, MsgDTO.class);
        return BaseResponse.successData(orderFacade.batchQuery(list));
    }

    /**
     * 单次查询
     * @param wId wId
     * @return List<WaybillBcList>
     */
    @GetMapping("/singleQuery")
    @ApiOperation(value = "单次查询",notes = "单次查询")
    public BaseResponse<WaybillBcList> singleQuery(@RequestParam("wId") String wId,@RequestParam("batchId") String batchId){
        return BaseResponse.successData(new WaybillBcList());
    }
}
