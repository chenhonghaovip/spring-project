package com.honghao.cloud.accountapi;

import java.math.BigDecimal;

/**
 * @author chenhonghao
 * @date 2020-08-12 19:58
 */
public class Test02 {
    public static void main(String[] args) {
        double num = 0.11;
        BigDecimal sum = BigDecimal.valueOf(num);

        BigDecimal basic = BigDecimal.valueOf(0.49);
        int i = 0;
        while (BigDecimal.valueOf(i*0.5).add(basic).compareTo(sum)<0){
            i++;
        }

        if (i>=1){
            BigDecimal max = BigDecimal.valueOf(i*0.5).add(basic);
            BigDecimal min = BigDecimal.valueOf((i-1)*0.5).add(basic);
            if (max.subtract(sum).compareTo(sum.subtract(min))>0){
                System.out.println(min.doubleValue());
            }else {
                System.out.println(max.doubleValue());
            }
        }else {
            System.out.println(0.49);
        }
    }
}
