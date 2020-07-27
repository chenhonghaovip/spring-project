package com.honghao.cloud.userapi.tactics;

import com.honghao.cloud.basic.common.base.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 快递
 *
 * @author chenhonghao
 * @date 2020-03-11 14:18
 */
@Slf4j
@Service
public class DeliveryServiceImpl implements KnightService {

    @Override
    public BaseResponse receiveShop(String data) {
        System.out.println("快递业务");
        return null;
    }
}
