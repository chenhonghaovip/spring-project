package com.honghao.cloud.userapi.test.face.file;

import com.honghao.cloud.basic.common.base.factory.ThreadPoolFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 多线程 大文件读取且去重
 * 思路：读取大文件，先按照长度进行切分，为每个线程分配需要读取的部分进行处理
 *
 * @author chenhonghao
 * @date 2020-06-03 09:07
 */
@Slf4j
public class SuperBigFileRead {
    private static int bufferSize = 1024;
    private long fileLength;
    private static RandomAccessFile rAccessFile;
    private static Set<StartEndPair> startEndPairs;

    private SuperBigFileRead(File file){
        this.fileLength = file.length();
        try {
            rAccessFile = new RandomAccessFile(file,"r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        startEndPairs = new HashSet<>();
    }
    private static ConcurrentHashMap<String,Integer> map = new ConcurrentHashMap<>();
    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(4,100,"file—import");


    public static void main(String[] args) {
        String path = "F:\\u02\\logs\\waybill\\waybill-DESKTOP-O5RAEHE.log";
        File file = new File(path);
        long length = file.length();
        long ever = length/threadPoolExecutor.getCorePoolSize();
        SuperBigFileRead bigFileReader = new SuperBigFileRead(file);
        try {
            bigFileReader.calculateStartEnd(0,ever);
        } catch (IOException e) {
            log.error(e.toString());
        }

        for(StartEndPair pair:startEndPairs){
            System.out.println("分配分片："+pair);
            threadPoolExecutor.execute(()->{
                try {
                    long sliceSize = pair.end - pair.start+1;
                    byte[] readBuff = new byte[bufferSize];

                    MappedByteBuffer mapBuffer = rAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, pair.start, sliceSize);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    mapBuffer.get(readBuff, 0, (int) sliceSize);
                    for (int i = 0; i < sliceSize; i++) {
                        byte tmp = readBuff[i];
                        //碰到换行符
                        if (tmp == '\n' || tmp == '\r') {
                            handle(bos.toByteArray());
                            bos.reset();
                        } else {
                            bos.write(tmp);
                        }
                    }
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            });
        }

    }

    private static void handle(byte[] array){
        System.out.println(Thread.currentThread().getName()+"===" + new String(array,StandardCharsets.UTF_8));
    }


    private void calculateStartEnd(long start,long size) throws IOException{
        if(start>fileLength-1){
            return;
        }
        StartEndPair pair = new StartEndPair();
        pair.start=start;
        long endPosition = start+size-1;
        if(endPosition>=fileLength-1){
            pair.end=fileLength-1;
            startEndPairs.add(pair);
            return;
        }
        // 定位到endPosition位置
        rAccessFile.seek(endPosition);
        byte tmp =(byte) rAccessFile.read();
        while(tmp!='\n' && tmp!='\r'){
            endPosition++;
            if(endPosition>=fileLength-1){
                endPosition=fileLength-1;
                break;
            }
            rAccessFile.seek(endPosition);
            tmp =(byte) rAccessFile.read();
        }
        pair.end=endPosition;
        startEndPairs.add(pair);

        calculateStartEnd(endPosition+1, size);
    }


    public static class StartEndPair {
        public long start;
        public long end;

        @Override
        public String toString() {
            return "star="+start+";end="+end;
        }

        @Override
        public int hashCode() {
            return (int) ((start+end*100)%Integer.MAX_VALUE);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof StartEndPair){
                StartEndPair startEndPair = (StartEndPair)obj;
                return startEndPair.start == start && startEndPair.end == end;
            }
            return false;
        }
    }
}
