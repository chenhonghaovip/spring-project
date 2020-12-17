package com.honghao.cloud.message.client;

import com.honghao.cloud.basic.common.base.BaseResponse;
import com.honghao.cloud.message.client.hystrix.AccountFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 账户服务接口
 *
 * @author chenhonghao
 * @date 2019-07-31 13:19
 */
@FeignClient(name = AccountClient.SERVICE_ID, fallbackFactory = AccountFallbackFactory.class)
public interface AccountClient {
    String SERVICE_ID = "account-api";

    /**
     * 修改消息状态
     *
     * @param msgInfoDTO msgInfoDTO
     * @return BaseResponse
     */
    @GetMapping("/messageController/message")
    BaseResponse businessStatus(@RequestParam("msgId") long msgInfoDTO);
}
