package com.honghao.cloud.accountapi.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 配置类
 *
 * @author chenhonghao
 * @date 2020-09-15 15:58
 */
@Configuration
public class LocalDateTimeConfig {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizeLocalDateTimeFormat() {
        return jacksonObjectMapperBuilder -> {

            LocalDateTimeDeserializer deserializer = new LocalDateTimeDeserializer(formatter);
            LocalDateTimeSerializer serializer = new LocalDateTimeSerializer(formatter);
            jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class, serializer);
            jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class, deserializer);
        };
    }
}
