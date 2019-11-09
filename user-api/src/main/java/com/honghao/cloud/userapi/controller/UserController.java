package com.honghao.cloud.userapi.controller;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.userapi.aspect.Auth;
import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.dto.request.CreateUserDTO;
import com.honghao.cloud.userapi.dto.request.Operator;
import com.honghao.cloud.userapi.dto.request.UpdateUserDTO;
import com.honghao.cloud.userapi.facade.WaybillBcListFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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
@Validated
public class UserController {
    @Resource
    private WaybillBcListFacade waybillBcListFacade;

    /**
     * 创建用户
     * @param data shuju
     * @return BaseResponse
     * @Auth
     */

    @PostMapping("/create")
    @ApiOperation(value = "创建用户",notes = "创建用户")
    BaseResponse<Boolean> createUser(@RequestBody String data) {
        waybillBcListFacade.createUser(data);
        return BaseResponse.success();
    }

    /**
     * 创建用户
     * @param data
     * @return
     */
    @Auth
    @PostMapping("/create1")
    @ApiOperation(value = "测试" ,notes = "测试")
    BaseResponse<String> getUser(@RequestBody String data) {
//        waybillBcListFacade.createUser1(data);
        waybillBcListFacade.createUser2("");
        return null;
    }

    /**
     * 创建用户
     * @param kappId 骑士id
     * @return BaseResponse
     */
//    @Auth
    @GetMapping("/create2")
    @ApiOperation(value = "测试" ,notes = "测试")
    BaseResponse<String> create2(@RequestParam("kappId") String kappId) {
        waybillBcListFacade.createUser2("");
        return null;
    }

    @PostMapping("/test001")
    BaseResponse test01(@RequestBody @Validated(Operator.First.class) CreateUserDTO createUserDTO){
        System.out.println(JSON.toJSONString(createUserDTO));
        return null;
    }


    @PostMapping("/test002")
    BaseResponse test02(@RequestBody @Valid UpdateUserDTO createUserDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
        }
        System.out.println(JSON.toJSONString(createUserDTO));
        return null;
    }
}
