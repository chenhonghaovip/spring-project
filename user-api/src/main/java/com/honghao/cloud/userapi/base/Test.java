package com.honghao.cloud.userapi.base;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenhonghao
 * @date 2019-07-19 02:23
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        List<BankAccountDTO> lis=new ArrayList<>();
        lis.add(new BankAccountDTO());
        lis.add(null);
        log.info(JSON.toJSONString(lis));
        for (BankAccountDTO li : lis) {
            li.getAccountType();
        }

    }
}
