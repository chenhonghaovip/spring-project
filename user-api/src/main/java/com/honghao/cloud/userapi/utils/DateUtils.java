package com.honghao.cloud.userapi.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换工具类
 *
 * @author chenhonghao
 * @date 2019-07-17 14:13
 */
public class DateUtils {
    private static final String STYLE_1 = "yyyy-MM-dd HH:mm:ss";

    /**
     * 转换日期为年月日时分秒的字符串格式
     *
     * @param date 日期
     * @return 时间字符串
     */
    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STYLE_1);
        return simpleDateFormat.format(date);
    }
}
