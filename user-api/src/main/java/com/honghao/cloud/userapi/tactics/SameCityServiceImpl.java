package com.honghao.cloud.userapi.tactics;

import com.honghao.cloud.basic.common.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author chenhonghao
 * @date 2020-03-11 14:17
 */
@Slf4j
@Service
public class SameCityServiceImpl implements KnightService {
    @Override
    public BaseResponse receiveShop(String data) {
        System.out.println("同城商家" + data);
        return null;
    }

}
