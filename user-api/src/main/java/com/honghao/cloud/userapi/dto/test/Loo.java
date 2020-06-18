package com.honghao.cloud.userapi.dto.test;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author chenhonghao
 * @date 2019-11-16 18:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loo {
    @JSONField(name = "name_1")
    private String name;

    @JSONField(name = "name_11")
    private String age;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8")
    private Date date;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate localDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime localTime;
}
