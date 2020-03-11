package com.honghao.cloud.userapi.controller;

import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import com.honghao.cloud.userapi.dto.response.SameCityNumVO;
import com.honghao.cloud.userapi.facade.SameCitySearchFacade;
import com.honghao.cloud.userapi.facade.SameCityServiceFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 同城订单控制
 *
 * @author chenhonghao
 * @date 2019-12-26 10:37
 */
@Slf4j
@RestController
@RequestMapping("/sameCity")
public class SameCityController {
    @Resource
    private SameCitySearchFacade sameCitySearchFacade;

    @Resource
    private SameCityServiceFacade sameCityServiceFacade;

    /**
     * 查询同城订单数量
     * @param knightId 骑士id
     * @return BaseResponse
     */
    @GetMapping("/getNum")
    public BaseResponse<List<SameCityNumVO>> getNum(@RequestParam("knightId") @NotNull String knightId){
        return BaseResponse.successData(sameCitySearchFacade.getNum(knightId));
    }

    public BaseResponse receive(@RequestBody WaybillBcList waybillBcList){
        sameCityServiceFacade.receive("chenhonghoa");
        return BaseResponse.success();
    }
}
