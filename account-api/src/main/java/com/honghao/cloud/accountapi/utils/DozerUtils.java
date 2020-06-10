package com.honghao.cloud.accountapi.utils;

import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;

/**
 * 对象属性复制工具类
 *
 * @author chenhonghao
 * @date 2019-07-30 17:31
 */
@Slf4j
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
