package com.honghao.cloud.userapi.service.impl;

import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import com.honghao.cloud.userapi.domain.mapper.master.WaybillBcListMapper;
import com.honghao.cloud.userapi.dto.easypoi.WaybillBcListEasyPoi;
import com.honghao.cloud.userapi.dto.service.SameCityNumDTO;
import com.honghao.cloud.userapi.service.WaybillBcListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单服务实现类
 *
 * @author chenhonghao
 * @date 2019-07-18 17:32
 */
@Slf4j
@Service
public class WaybillBcListServiceImpl implements WaybillBcListService {
    @Resource
    private WaybillBcListMapper waybillBcListMapper;


    @Override
    public void createUser(WaybillBcList waybillBcList) {
        waybillBcListMapper.insert(waybillBcList);
    }

    @Override
    public List<WaybillBcListEasyPoi> selectOrders() {
        List<WaybillBcList> lists = new ArrayList<>();

        WaybillBcListEasyPoi waybillBcListEasyPoi;
        List<WaybillBcListEasyPoi> result = new ArrayList<>();
        for (WaybillBcList list : lists) {
            waybillBcListEasyPoi = new WaybillBcListEasyPoi();
            BeanUtils.copyProperties(list,waybillBcListEasyPoi);
            result.add(waybillBcListEasyPoi);
        }
        return result;
    }

    @Override
    public int updateInfos(WaybillBcList waybillBcList) {
        return waybillBcListMapper.updateByPrimaryKeySelective(waybillBcList);
    }

    @Override
    public List<SameCityNumDTO> getNum(String knightId) {
        return waybillBcListMapper.getNum(knightId);
    }

    @Override
    public List<WaybillBcList> orderList() {
        return null;
    }


}
