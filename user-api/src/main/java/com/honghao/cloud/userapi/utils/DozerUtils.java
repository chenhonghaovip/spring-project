package com.honghao.cloud.userapi.utils;

import com.honghao.cloud.userapi.dto.common.DozerDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

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
     *
     * @param source 资源
     * @param clazz  目标类
     * @param <T>    资源类
     * @param <U>    目标类
     */
    public static <T, U> void map(T source, Class<U> clazz) {
        dozerBeanMapper.map(source, clazz);
    }

    /**
     * 批量转换赋值
     *
     * @param sourceList 资源list
     * @param clazz      目标类
     * @param <T>        资源类
     * @param <U>        目标类
     * @return 目标类list
     */
    public static <T, U> List<U> mapList(List<T> sourceList, Class<U> clazz) {
        List<U> list = new ArrayList<>();
        for (T t : sourceList) {
            list.add(dozerBeanMapper.map(t, clazz));
        }
        return list;
    }

    /**
     * 将Collection<E>批量转换赋值
     *
     * @param source 资源list
     * @param clazz  目标类
     * @param <T>    资源类
     * @param <U>    目标类
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
     *
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


    /**
     * 属性复制，不覆盖原有值
     *
     * @param sourceBean 资源对象
     * @param targetBean 目标对象
     * @param <T>        资源类型
     * @param <U>        目标类型
     */
    public static <T, U> void customizeMap(T sourceBean, U targetBean) {
        List<DozerDTO> target = Arrays.stream(targetBean.getClass().getDeclaredFields()).map(each -> DozerDTO.builder().field(each).name(each.getName()).build())
                .collect(Collectors.toList());

        Map<String, List<Field>> fieldMap = Arrays.stream(sourceBean.getClass().getDeclaredFields()).collect(Collectors.groupingBy(Field::getName));

        for (DozerDTO dozerDTO : target) {
            try {
                dozerDTO.getField().setAccessible(true);
                if (dozerDTO.getField().get(targetBean) != null) {
                    continue;
                }
                if (CollectionUtils.isNotEmpty(fieldMap.get(dozerDTO.getName()))) {
                    Field field = fieldMap.get(dozerDTO.getName()).get(0);
                    field.setAccessible(true);
                    dozerDTO.getField().set(targetBean, field.get(sourceBean));
                }
            } catch (IllegalAccessException e) {
                log.error(e.getMessage());
            }
        }
    }

}
