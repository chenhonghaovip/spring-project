package com.honghao.cloud.userapi.spring.importin;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author chenhonghao
 * @date 2020-01-13 21:07
 */
public class ImportSelectTest implements ImportSelector {
    /**
     *
     * @param annotationMetadata annotationMetadata 当前标注@Import注解的类上的所有的注解信息
     * @return
     */
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"com.honghao.cloud.userapi.spring.bean.AppleD"};
    }
}