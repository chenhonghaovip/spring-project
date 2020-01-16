package com.honghao.cloud.userapi.spring;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author chenhonghao
 * @date 2020-01-13 21:07
 */
public class ImportSelectTest implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"com.honghao.cloud.userapi.spring.AppleD"};
    }
}
