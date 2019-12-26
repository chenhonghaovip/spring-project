package com.honghao.cloud.userapi.facade.impl;

import com.honghao.cloud.userapi.dto.response.SameCityNumVO;
import com.honghao.cloud.userapi.dto.service.SameCityNumDTO;
import com.honghao.cloud.userapi.facade.SameCitySearchFacade;
import com.honghao.cloud.userapi.service.WaybillBcListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 同城订单查询服务
 *
 * @author chenhonghao
 * @date 2019-12-26 10:48
 */
@Slf4j
@Service
public class SameCitySearchFacadeImpl implements SameCitySearchFacade {
    @Resource
    private WaybillBcListService waybillBcListService;


    @Override
    public List<SameCityNumVO> getNum(String knightId) {
        List<SameCityNumDTO> list = waybillBcListService.getNum(knightId);

        return list.stream().map(each -> SameCityNumVO.builder().orderStatus(each.getOrderStatus()).num(each.getNum()).build()).collect(Collectors.toList());
    }
}
