package com.honghao.cloud.userapi.service;

import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import com.honghao.cloud.userapi.dto.easypoi.WaybillBcListEasyPoi;
import com.honghao.cloud.userapi.dto.service.SameCityNumDTO;

import java.util.List;

/**
 * 订单服务接口
 *
 * @author chenhonghao
 * @date 2019-07-18 17:31
 */
public interface WaybillBcListService {
    /**
     * 插入用户数据
     * @param waybillBcList waybillBcListService
     */
    void createUser(WaybillBcList waybillBcList);

    List<WaybillBcListEasyPoi> selectOrders();

    int updateInfos(WaybillBcList waybillBcList);

    /**
     * 查询同城订单数量
     * @param knightId 骑士id
     * @return BaseResponse
     */
    List<SameCityNumDTO> getNum(String knightId);
}
