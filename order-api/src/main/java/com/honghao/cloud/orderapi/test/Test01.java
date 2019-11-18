package com.honghao.cloud.orderapi.test;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.honghao.cloud.orderapi.dto.test.Test;

/**
 * @author chenhonghao
 * @date 2019-10-17 11:24
 */
public class Test01 {
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name_1","111");
        Test test = JSON.parseObject(jsonObject.toJSONString(), Test.class);
        System.out.println(test.getName1());
        System.out.println(JSON.toJSONString(test));
    }
}
