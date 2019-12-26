package com.honghao.cloud.userapi.facade;

import com.honghao.cloud.userapi.dto.response.SameCityNumVO;

import java.util.List;

/**
 * 同城订单查询服务
 *
 * @author chenhonghao
 * @date 2019-12-26 10:46
 */
public interface SameCitySearchFacade {

    /**
     * 查询同城订单数量
     * @param knightId 骑士id
     * @return BaseResponse
     */
    List<SameCityNumVO> getNum(String knightId);
}
