package com.honghao.cloud.userapi.test.springtest;

import com.honghao.cloud.userapi.test.HashMap;

/**
 * @author chenhonghao
 * @date 2020-01-16 08:59
 */
public class HashTest {
    public static void main(String[] args) {
        Map map = new HashMap(8);
        map.put("chen","chen");
        map.put("hong","hong");
        map.put("hao","hao");
        System.out.println(map.get("hao"));
        System.out.println(map.get("chen"));
    }
}
