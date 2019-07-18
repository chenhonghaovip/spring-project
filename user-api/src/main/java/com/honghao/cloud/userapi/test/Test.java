package com.honghao.cloud.userapi.test;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @author chenhonghao
 * @date 2019-07-17 09:23
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        //数字字符串
        String StrBd="1048576.1024";
//构造以字符串内容为值的BigDecimal类型的变量bd
        BigDecimal bd=new BigDecimal(StrBd);
//设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
        bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP);
//转化为字符串输出
        String OutString=bd.toString();
        System.out.println(bd);
    }
}
