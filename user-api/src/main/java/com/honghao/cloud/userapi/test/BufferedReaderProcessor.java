package com.honghao.cloud.userapi.test;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author chenhonghao
 * @date 2019-12-02 11:28
 */
@FunctionalInterface
public interface BufferedReaderProcessor {
    /**
     * java8测试
     * @param b b
     * @return String
     * @throws IOException io
     */
    String process(BufferedReader b) throws IOException;
}
