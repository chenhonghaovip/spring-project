package com.honghao.cloud.userapi.test;

import com.alibaba.fastjson.JSONObject;
import com.honghao.cloud.userapi.base.BankAccountDTO;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

/**
 * 测试1
 *
 * @author chenhonghao
 * @date 2019-08-12 16:31
 */
@Slf4j
public class Test01 extends AbstractTest{
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
//        long tiem = System.currentTimeMillis();
//        System.out.println(tiem);
//        String s = "15722495220000";
//         sdf.format(tiem);
        String tiem = "2019-09-23 13:23:06";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("time1",tiem);
        jsonObject.put("time2",System.currentTimeMillis());
        System.out.println(jsonObject.getDate("time1").getTime() - System.currentTimeMillis());
//        try {
//            System.out.println(sdf.parse(String.valueOf(tiem)).getTime());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        double time2 = 1515730332000d;
//        String result2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time2);
//        System.out.println("13位数的时间戳（毫秒）--->Date:" + result2);

    }
}
