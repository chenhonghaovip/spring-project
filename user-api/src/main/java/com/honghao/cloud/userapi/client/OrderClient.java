package com.honghao.cloud.userapi.client;

import com.alibaba.fastjson.JSONObject;
import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.userapi.client.hystrix.OrderClientFallbackFactory;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Bap服务操作接口
 *
 * @author chenhonghao
 * @date 2019-05-14
 */
@FeignClient(
        name = OrderClient.SERVER_ID
        ,fallbackFactory = OrderClientFallbackFactory.class
)
public interface OrderClient {
    String SERVER_ID = "ORDER-API";

    /**
     * 批量删除token
     * @param data string
     * @return 删除
     */
    @PostMapping("/order/create")
    BaseResponse<String> createUser(@RequestBody JSONObject data);

    /**
     * 批量删除token
     * @param data string
     * @return 删除
     */
    @PostMapping("/order/create1")
    BaseResponse<String> createUser1(@RequestParam String data);

    /**
     * 批次查询
     * @param list list
     * @return List<WaybillBcList>
     */
    @PostMapping("/order/batchQuery")
    BaseResponse<List<WaybillBcList>> batchQuery(@RequestBody List<String> list);


    /**
     * 查询
     * @param wId 订单号
     * @param batchId 批次号
     * @return BaseResponse
     */
    @GetMapping("/singleQuery")
    BaseResponse<WaybillBcList> singleQuery(@RequestParam("wId") String wId, @RequestParam("batchId") String batchId);
}
