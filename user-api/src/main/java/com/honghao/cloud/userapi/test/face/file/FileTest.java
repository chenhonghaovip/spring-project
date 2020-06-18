package com.honghao.cloud.userapi.test.face.file;

import com.honghao.cloud.userapi.factory.ExecutorFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 文件批量导入
 *
 * @author chenhonghao
 * @date 2020-05-25 15:50
 */
@Slf4j
public class FileTest {
    private static ConcurrentHashMap<String,Integer> map = new ConcurrentHashMap<>();
    ThreadPoolExecutor threadPoolExecutor = ExecutorFactory.buildThreadPoolExecutor(4,10,"file—import");

    public static void main(String[] args) {
        String path = "F:\\u02\\logs\\waybill";
        FileTest fileTest = new FileTest();
        fileTest.test(path);
    }
    public void test(String path){
        File file = new File(path);
        File[] tempList = file.listFiles();
        assert tempList != null;
        List<CompletableFuture<Void>> list = new ArrayList<>();
        Arrays.stream(tempList).forEach(each-> {
            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
                try {
                    test02(each.toString());
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }, threadPoolExecutor);
            list.add(voidCompletableFuture);
        });
        CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).whenComplete((r,t)->{
            System.out.println(map.keySet());
        });
    }

    private void test01(String fileName) throws IOException {
        BufferedReader reader = null;
        try {
            File file = new File(fileName);

            reader = new BufferedReader(new FileReader(file));
            String tempString;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                map.put(tempString,line);
                line++;
            }

        } finally {
            if (reader!=null){
                reader.close();
            }
        }
    }

    private void test02(String fileName) throws IOException {
        File file = new File(fileName);
        try (FileInputStream reader = new FileInputStream(file); Scanner sc = new Scanner(reader, "UTF-8")) {
            String tempString;
            while ((tempString = sc.nextLine()) != null) {
                // 显示行号
                map.put(tempString, 1);
            }
        }
    }
}
