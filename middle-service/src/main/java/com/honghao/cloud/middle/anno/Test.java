package com.honghao.cloud.middle.anno;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

public class Test {


    @org.junit.Test
    public void test() {
        TestDTO testDTO = new TestDTO();
        testDTO.setName("chen");
        testDTO.setSex("man");

        Map<String, Object> maps = Maps.newHashMap();
        Class<TestDTO> testDTOClass = TestDTO.class;
        Field[] fields = testDTOClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
//            String name = field.getName();
            EsFieldMapping annotation = field.getAnnotation(EsFieldMapping.class);
            String mappingName = field.getName();
            if (Objects.nonNull(annotation) && StringUtils.isNotBlank(annotation.alias())) {
                mappingName = annotation.alias();
            }
            Object o = null;
            try {
                o = field.get(testDTO);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (Objects.nonNull(o)) {
                maps.put(mappingName, o);
            }
        }

        System.out.println(JSON.toJSONString(maps));
    }
}
