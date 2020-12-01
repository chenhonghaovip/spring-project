package com.honghao.cloud.basic.common.context;

import java.util.ServiceLoader;

/**
 * @author chenhonghao
 * @date 2020-11-23 11:43
 */
public class Test {
    public static void main(String[] args) {
        ServiceLoader<UploadSpi> loads = ServiceLoader.load(UploadSpi.class);
        for (UploadSpi u : loads) {
            u.upload("filePath");
        }
    }
}
