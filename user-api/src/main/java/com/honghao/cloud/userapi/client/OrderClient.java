package com.honghao.cloud.userapi.client;

import com.honghao.cloud.userapi.aspect.FeignExceptionDeal;
import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import org.springframework.cloud.openfeign.FeignClient;
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
//        ,fallbackFactory = OrderClientFallbackFactory.class
)
public interface OrderClient {
    String SERVER_ID = "ORDER-API";

    /**
     * 批量删除token
     * @param data string
     * @return 删除
     */
    @FeignExceptionDeal
    @PostMapping("/order/create")
    BaseResponse<String> createUser(@RequestParam String data);

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
    List<WaybillBcList> batchQuery(@RequestBody List<String> list);
}
