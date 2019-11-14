package com.honghao.cloud.userapi.test;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.userapi.dto.request.TestDTO;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chenhonghao
 * @date 2019-11-11 19:58
 */
@Slf4j
public class Test03 {
    public static void main(String[] args) {
        TestDTO testDTO = new TestDTO("chen",1, BigDecimal.valueOf(2.32),2.54);
        Set<TestDTO> set = new HashSet<>();
        set.add(testDTO);

        TestDTO testDTO1 = new TestDTO("chen",1, BigDecimal.valueOf(2.32),2.54);
        set.add(testDTO1);
        set.stream().forEach(each -> System.out.println(JSON.toJSONString(each)) );

    }
}
