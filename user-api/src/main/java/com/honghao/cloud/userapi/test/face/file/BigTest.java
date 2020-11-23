package com.honghao.cloud.userapi.test.face.file;

import com.honghao.cloud.basic.common.factory.ThreadPoolFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chenhonghao
 * @date 2020-06-03 16:26
 */
@Slf4j
public class BigTest {
    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolFactory.buildThreadPoolExecutor(4,10,"t4est");
    private static List<StartEndPair> list = new ArrayList<>();
    private static long fileSize;
    private static RandomAccessFile rAccessFile;

    public static void main(String[] args) {
        File file = new File("F:\\u02\\logs\\waybill\\waybill-DESKTOP-O5RAEHE.log");
        fileSize = file.length();
        BigTest bigTest= new BigTest();
        long size = fileSize/threadPoolExecutor.getCorePoolSize();
        try {
            rAccessFile = new RandomAccessFile(file,"r");
            bigTest.calculate(0,size);
        } catch (IOException e) {
            log.error(e.toString());
        }

        List<CompletableFuture<Void>> result = new ArrayList<>();
        list.forEach(each->{
            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
                long temp = each.getEnd() - each.getStart() + 1;
                int core = (int) temp;
                byte[] readBuff = new byte[core];
                try {
                    MappedByteBuffer mappedByteBuffer = rAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, each.getStart(), temp);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    mappedByteBuffer.get(readBuff, 0, core);

                    for (int i = 0; i < readBuff.length; i++) {
                        if (readBuff[i] == '\n' || readBuff[i] == '\r') {
                            System.out.println(Thread.currentThread().getName() + "====" + new String(bos.toByteArray(), StandardCharsets.UTF_8));
                            bos.reset();
                        } else {
                            bos.write(readBuff[i]);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, threadPoolExecutor);
            result.add(voidCompletableFuture);
        });
        CompletableFuture.allOf(result.toArray(new CompletableFuture[0])).whenComplete((t,e)->{
           threadPoolExecutor.shutdown();
        });
    }

    private void calculate(long start,long size) throws IOException {
        StartEndPair startEndPair = new StartEndPair();
        startEndPair.setStart(start);
        long end = start+size-1;
        if (end > fileSize){
            end = fileSize-1;
            startEndPair.setEnd(end);
            list.add(startEndPair);
            return;
        }

        rAccessFile.seek(end);
        byte b = rAccessFile.readByte();
        long nowRead = end;
        while (b!='\r' && b!='\n'){
            nowRead++;
            if (nowRead >= fileSize){
                startEndPair.setEnd(nowRead);
                list.add(startEndPair);
                break;
            }
            rAccessFile.seek(nowRead);
            b = rAccessFile.readByte();
        }

        startEndPair.setEnd(nowRead);
        list.add(startEndPair);
        calculate(nowRead+1,size);
    }

    public static class StartEndPair{
        private long start;
        private long end;

        public long getStart() {
            return start;
        }

        public void setStart(long start) {
            this.start = start;
        }

        public long getEnd() {
            return end;
        }

        public void setEnd(long end) {
            this.end = end;
        }
    }
}
