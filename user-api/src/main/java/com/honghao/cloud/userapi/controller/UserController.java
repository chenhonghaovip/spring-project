package com.honghao.cloud.userapi.controller;

import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.config.ThreadPoolInitConfig;
import com.honghao.cloud.userapi.facade.WaybillBcListFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

/**
 * 用户信息controller
 *
 * @author chenhonghao
 * @date 2019-07-17 17:22
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api("用户接口服务")
public class UserController {
    @Resource
    private WaybillBcListFacade waybillBcListFacade;

//    @Auth
    @PostMapping("/create")
    @ApiOperation(value = "创建用户",notes = "创建用户")
    BaseResponse<Boolean> createUser(@RequestBody String data) {
        waybillBcListFacade.createUser(data);
        return BaseResponse.success();
    }

    @PostMapping("/create1")
    @ApiOperation(value = "测试" ,notes = "测试")
    BaseResponse<String> getUser(@RequestBody String data) {
        Executor executor= ThreadPoolInitConfig.build("create");
//        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(2,10,60, TimeUnit.SECONDS,new LinkedBlockingQueue<>());
//        ThreadPoolExecutor threadPoolExecutor= ThreadPoolInitConfig.b
        executor.execute(()-> log.info("1111111"));

        return null;
    }
}
