package com.honghao.cloud.userapi.facade.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.honghao.cloud.userapi.dto.request.UserDTO;
import com.honghao.cloud.userapi.facade.SameCityServiceFacade;
import com.honghao.cloud.userapi.utils.JedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 同城订单操作服务
 *
 * @author chenhonghao
 * @date 2019-12-26 10:47
 */
@Slf4j
@Service
public class SameCityServiceFacadeImpl implements SameCityServiceFacade {
    @Resource
    private JedisOperator jedisOperator;
    @Override
    public void receive(String data) {
        List<UserDTO> list = new ArrayList<>();
        list.add(new UserDTO("chen",13,"543535435"));
        list.add(new UserDTO("hong",15,"123131231"));
        jedisOperator.set("chenhonghao", JSON.toJSONString(list));

        List<UserDTO> result = JSONArray.parseArray(jedisOperator.get("chenhonghao"),UserDTO.class);
        log.info("{}",result);
    }
}
