package com.honghao.cloud.userapi.test.face.file;

import com.honghao.cloud.basic.common.factory.ThreadPoolFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 大文件读取且去重
 * 思路：读取大文件，将读取到的每一行进行hash模运算，模相同的写入到同一个文件，各个小文件再继续进行去重操作，最后合并结果集就可以
 *
 * @author chenhonghao
 * @date 2020-06-03 09:07
 */
@Slf4j
public class BigFileRead {
    private static ConcurrentHashMap<String,Integer> map = new ConcurrentHashMap<>();
    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(20,100,"file—import");


    public static void main(String[] args) {
        String path = "F:\\u02\\logs\\waybill";
        File file = new File(path);
        File[] files = file.listFiles();
        List<CompletableFuture<Void>> list = new ArrayList<>();

        File[] result = new File[10];
        for (int i = 0; i < 10; i++) {
            try {
                File file1 = new File(path+File.separator+i+".txt");
                if (!file1.exists()){
                    file1.createNewFile();
                }
                result[i] = file1;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        assert files!=null;
        LocalDateTime now = LocalDateTime.now();
        Arrays.stream(files).forEach(each->{
            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() ->{
                try {
                    read(result,each);
                } catch (IOException e) {
                    log.error(e.toString());
                }
            },threadPoolExecutor);
            list.add(voidCompletableFuture);
        });
        CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).whenComplete((t,r)->{
            Duration duration = Duration.between(LocalDateTime.now(),now);
            System.out.println("is over" + duration.toMillis()/1000);
        });
        threadPoolExecutor.shutdown();
    }

    private static void read(File[] result,File each) throws IOException{
        BufferedReader bufferedReader = null;
        BufferedWriter temp = null;
        List<BufferedWriter> list = new ArrayList<>();
        Arrays.asList(result).forEach(other->{
            try {
                list.add(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(other, true))));
            } catch (FileNotFoundException e) {
                log.error(e.toString());
            }
        });

        try {
            bufferedReader = new BufferedReader(new FileReader(each));
            String tempString;
            while ((tempString = bufferedReader.readLine())!=null){
                int hashCode = Math.abs(tempString.hashCode()%10);
                temp = list.get(hashCode);
                temp.newLine();
                temp.write(tempString);
                temp.flush();
            }
        } finally {
            if (bufferedReader!=null){
                bufferedReader.close();
            }
            if (temp!=null){
                temp.close();
            }
        }
    }
}
