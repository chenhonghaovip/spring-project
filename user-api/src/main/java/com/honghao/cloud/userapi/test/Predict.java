package com.honghao.cloud.userapi.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author chenhonghao
 * @date 2019-12-02 22:12
 */
public class Predict {
    public static void main(String[] args) {
        try {
            String result = processFile((BufferedReader br) ->
                    br.readLine() + br.readLine());
            String oneLine =
                    processFile((BufferedReader br) -> br.readLine());
            String twoLines =
                    processFile((BufferedReader br) -> br.readLine() + br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String processFile(BufferedReaderProcessor p) throws
            IOException {
        try (BufferedReader br =
                     new BufferedReader(new FileReader("data.txt"))) {
            return p.process(br);
        }
    }

}
