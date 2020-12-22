package com.honghao.cloud.userapi.test;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author chenhonghao
 * @date 2020-01-03 13:39
 */
public class PropertiesLoaderUtilsTest {

    private static final String FACTORIES_RESOURCE_LOCATION = "META-INF/shareniu-single.factories";

    public static void main(String[] args) throws IOException {
        Properties properties = PropertiesLoaderUtils.loadAllProperties(FACTORIES_RESOURCE_LOCATION, null);
        System.out.println(properties);
    }
}
