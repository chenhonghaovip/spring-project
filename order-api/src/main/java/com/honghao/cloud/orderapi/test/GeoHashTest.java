package com.honghao.cloud.orderapi.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * GEOHash测试
 *
 * @author chenhonghao
 * @date 2019-09-29 16:56
 */
@Slf4j
public class GeoHashTest {
    final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                        '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p',
                        'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

    public static void main(String[] args) {
        BigDecimal low = BigDecimal.valueOf(-90);
        BigDecimal high = BigDecimal.valueOf(90);
        GeoHashTest geoHashTest = new GeoHashTest();
        List<String> list = Arrays.asList();
        List<String> list1 = Arrays.asList();
        //纬度范围
        List<String> laLodeList = geoHashTest.getLaCode(BigDecimal.valueOf(39.92324),low,high,"",list );

//        String laLode = String.join(",", laLodeList).replaceAll(",","");
        //经度范围
        List<String> loCodeList = geoHashTest.getLaCode(BigDecimal.valueOf(116.3906),BigDecimal.valueOf(-180),BigDecimal.valueOf(180),"",list1);
//        String loCode = String.join(",", loCodeList).replaceAll(",","");
//        log.info("纬度范围:{}",laLode);
//        log.info("经度范围:{}",loCode);
//        System.out.println(laLodeList);
//        System.out.println(loCodeList);
//        String areaCode = geoHashTest.getAreaCode(loCodeList,laLodeList);
//        log.info(areaCode);
    }

    /**
     * 获取纬度编码
     * @param value 值
     * @param low 值
     * @param high 值
     * @param code 纬度编码
     * @return String
     */
    private List<String> getLaCode(BigDecimal value,BigDecimal low,BigDecimal high ,String code,List<String> list){
        if (StringUtils.isNotBlank(code) && code.length() >= 20){
            return list;
        }
        BigDecimal middle;
        middle = high.add(low).divide(BigDecimal.valueOf(2));

        if (value.compareTo(middle) > 0){
            low = middle;
            code = code + "1";
            list.add("1");
        }else if (value.compareTo(middle) < 0){
            high = middle;
            code = code + "0";
            list.add("0");
        }
        getLaCode(value,low,high,code,list);
        return list;
    }

    /**
     * 获取区域编码
     * @param lo 经度
     * @param la 纬度
     * @return string
     */
    private String getAreaCode(List<String> lo,List<String> la){
        List<String> areaCodeList = new ArrayList<>();
        for (int i = 0; i < la.size(); i++) {
            areaCodeList.add(lo.get(i));
            areaCodeList.add(la.get(i));
        }
        return String.join(",", areaCodeList).replaceAll(",","");
    }

    private void base32(){

    }
}
