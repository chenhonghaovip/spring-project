package com.honghao.cloud.userapi.test;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.userapi.base.BankAccountDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试1
 *
 * @author chenhonghao
 * @date 2019-08-12 16:31
 */
@Slf4j
public class Test01 extends AbstractTest{
    @Override
    public boolean getInfo() {
        return false;
    }

    @Override
    public BankAccountDTO remarkInfo() {
        return BankAccountDTO.builder().bankCode("222222")
                .name("chenhonghao").build();
    }

    public static void main(String[] args) {
        Test01 test01 = new Test01();
        test01.getInfo();
        log.info("flag:{}",test01.getInfo());
        log.info("remark = {}", JSON.toJSONString(test01.remarkInfo()));
    }
}
