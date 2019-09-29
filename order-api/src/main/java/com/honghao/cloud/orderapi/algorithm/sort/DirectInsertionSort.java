package com.honghao.cloud.orderapi.algorithm.sort;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

/**
 * 直接插入排序
 *
 * @author chenhonghao
 * @date 2019-09-23 16:43
 */
@Slf4j
public class DirectInsertionSort {
    public static void main(String[] args) {
        List list;
        int[] arr = new int[]{45,1233,5,34,40,2,12,232};
        list = Arrays.asList(arr);
        log.info("{}", JSON.toJSONString(list));
        int[] out = new int[20];
        for (int i = 0; i < list.size(); i++) {

        }

    }
}
