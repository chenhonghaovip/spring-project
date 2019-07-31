package com.honghao.cloud.userapi.utils;

import org.dozer.DozerBeanMapper;

/**
 * 对象属性复制工具类
 *
 * @author chenhonghao
 * @date 2019-07-30 17:31
 */
public class DozerUtils {
    private static volatile DozerBeanMapper dozerBeanMapper;
    private DozerUtils(){}
    public static DozerBeanMapper createDozer(){
        if (dozerBeanMapper==null){
            synchronized (dozerBeanMapper){
                if (dozerBeanMapper==null){
                    dozerBeanMapper=new DozerBeanMapper();
                }
            }
        }
        return dozerBeanMapper;
    }
}
