package com.honghao.cloud.userapi.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 驼峰格式转换工具
 *
 * @author chenhonghao
 * @date 2019-11-21 14:48
 */
@Slf4j
public class JsonUtil {
    /**
     * 将对象的大写转换为下划线加小写，例如：userName-->user_name
     *
     * @param object object
     * @return String
     */
    public static String changeToHump(Object object){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
           log.error(e.getMessage());
        }
        return null;
    }


    /**
     * 将下划线转换为驼峰的形式，例如：user_name-->userName
     *
     * @param json json字符串
     * @param clazz 转换类型
     * @return T
     */
    public static <T> T toSnakeObject(String json, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        T reqJson = null;
        try {
            reqJson = mapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return reqJson;
    }

}