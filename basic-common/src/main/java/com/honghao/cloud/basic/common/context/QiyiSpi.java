package com.honghao.cloud.basic.common.context;

/**
 * @author chenhonghao
 * @date 2020-11-23 11:37
 */
public class QiyiSpi implements UploadSpi{
    @Override
    public void upload(String url) {
        System.out.println("i love iqiyi");
    }
}
