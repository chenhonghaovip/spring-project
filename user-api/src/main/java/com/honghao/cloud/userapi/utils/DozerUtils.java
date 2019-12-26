package com.honghao.cloud.userapi.utils;

import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 对象属性复制工具类
 *
 * @author chenhonghao
 * @date 2019-07-30 17:31
 */
@Slf4j
public class DozerUtils {
    private static DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

    /**
     * 类型转换赋值
     * @param source 资源
     * @param clazz 目标类
     * @param <T> 资源类
     * @param <U> 目标类
     */
    public static <T,U> void map(T source, Class<U> clazz){
       dozerBeanMapper.map(source,clazz);
    }

    /**
     * 批量转换赋值
     * @param sourceList 资源list
     * @param clazz 目标类
     * @param <T> 资源类
     * @param <U> 目标类
     * @return 目标类list
     */
    public static <T,U> List<U> mapList(List<T> sourceList,Class<U> clazz){
        List<U> list = new ArrayList<>();
        for (T t : sourceList) {
            list.add(dozerBeanMapper.map(t,clazz));
        }
        return list;
    }

    /**
     * 将Collection<E>批量转换赋值
     * @param source 资源list
     * @param clazz 目标类
     * @param <T> 资源类
     * @param <U> 目标类
     * @return 目标类list
     */
    public static <T, U> Collection<U> mapCollection(final Collection<T> source, final Class<U> clazz) {
        final Collection<U> dest = new ArrayList<>();
        for (T element : source) {
            dest.add(dozerBeanMapper.map(element, clazz));
        }
        return dest;
    }

    /**
     * 将source的所有属性拷贝至target,source里没有的字段,target里不覆盖
     * @param source 资源
     * @param target 目标对象
     * @return 目标对象
     */
    public static <U> void map(final Object source, final U target) {
        dozerBeanMapper.addMapping(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(source.getClass(), target.getClass(),
                        TypeMappingOptions.mapNull(false));
            }

        });
        dozerBeanMapper.map(source, target);
    }


    public static <T,U> U customizeMap(T sourceBean, U targetBean) {
        Field[] sourceFields = sourceBean.getClass().getDeclaredFields();
        Field[] targetFields = targetBean.getClass().getDeclaredFields();
        List<Field> fields = Arrays.asList(targetFields);

        for (Field sourceField : sourceFields) {
            if (fields.contains(sourceField)){

            }
        }
        return targetBean;

    }



}
