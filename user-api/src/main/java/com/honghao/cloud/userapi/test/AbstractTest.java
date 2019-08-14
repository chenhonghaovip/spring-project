package com.honghao.cloud.userapi.test;

import com.honghao.cloud.userapi.base.BankAccountDTO;

/**
 * 抽象测试类
 *
 * @author chenhonghao
 * @date 2019-08-12 16:29
 */
public abstract class AbstractTest {
    /**
     * 获取信息
     * @return boolean
     */
    public abstract boolean getInfo();

    public BankAccountDTO remarkInfo(){
        return BankAccountDTO.builder().bankCode("1111").build();
    }
}
