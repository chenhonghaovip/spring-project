package com.honghao.cloud.basic.common.context;

/**
 * spi测试接口
 *
 * @author chenhonghao
 * @date 2020-11-23 11:27
 */
public interface UploadSpi {
    /**
     * 上传接口
     *
     * @param url url
     */
    void upload(String url);
}
