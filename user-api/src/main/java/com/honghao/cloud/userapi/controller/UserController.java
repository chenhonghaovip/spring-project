package com.honghao.cloud.userapi.controller;

import com.honghao.cloud.basic.common.base.base.BaseAssert;
import com.honghao.cloud.basic.common.base.base.BaseResponse;
import com.honghao.cloud.userapi.common.enums.ErrorCodeEnum;
import com.honghao.cloud.userapi.facade.WaybillBcListFacade;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;

/**
 * 用户信息controller
 *
 * @author chenhonghao
 * @date 2019-07-17 17:22
 */
@Slf4j
@Validated
@Api("用户接口服务")
@RestController
@RequestMapping("/user")
public class UserController {
    private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    @Resource
    private WaybillBcListFacade waybillBcListFacade;


    @GetMapping("/test")
    public BaseResponse test(@RequestParam("wId") String data){
        BaseAssert.isNull(data,ErrorCodeEnum.OBJECT_CANNOT_BE_EMPTY);
        return BaseResponse.success();
    }
}
