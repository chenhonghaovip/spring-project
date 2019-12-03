package com.honghao.cloud.userapi.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author chenhonghao
 * @date 2019-12-02 11:07
 */
public class AppleProxy {

    public static void main(String[] args) {
        try {
            processFile(BufferedReader::readLine);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static String processFile() throws IOException {
        try (BufferedReader br =
                     new BufferedReader(new FileReader("data.txt"))) {
            return br.readLine();
        }
    }

    private static String processFile(BufferedReaderProcessor processor) throws IOException {
        try (BufferedReader br =
                     new BufferedReader(new FileReader("data.txt"))) {
           return processor.process(br);
        }

    }

}
