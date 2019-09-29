package com.honghao.cloud.orderapi.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * GEOHash测试
 *
 * @author chenhonghao
 * @date 2019-09-29 16:56
 */
@Slf4j
public class GeoHashTest {
    public static void main(String[] args) {
        BigDecimal low = BigDecimal.valueOf(-90);
        BigDecimal high = BigDecimal.valueOf(90);
        GeoHashTest geoHashTest = new GeoHashTest();
        String code = geoHashTest.getLaCode(BigDecimal.valueOf(39.92324),low,high,"");
        log.info(code);

    }

    /**
     * 获取纬度编码
     * @param value 值
     * @param low 值
     * @param high 值
     * @param code 纬度编码
     * @return String
     */
    public String getLaCode(BigDecimal value,BigDecimal low,BigDecimal high ,String code){
        log.info("code为：{}",code);
        if (StringUtils.isNotBlank(code) && code.length() >= 20){
            return code;
        }
        BigDecimal middle;
        middle = high.add(low).divide(BigDecimal.valueOf(2));

        if (value.compareTo(middle) > 0){
            low = middle;
            code = code + "1";
        }else if (value.compareTo(middle) < 0){
            high = middle;
            code = code + "0";
        }
        getLaCode(value,low,high,code);
        return code;
    }
}
