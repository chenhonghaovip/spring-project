package com.honghao.cloud.basic.common.context;

/**
 * @author chenhonghao
 * @date 2020-11-23 11:38
 */
public class ChinaNetSpi implements UploadSpi {
    @Override
    public void upload(String url) {
        System.out.println("ChinaNetSpi");
    }
}
