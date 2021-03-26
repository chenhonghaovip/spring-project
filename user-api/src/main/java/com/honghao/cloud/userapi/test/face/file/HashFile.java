package com.honghao.cloud.userapi.test.face.file;

import org.junit.Test;
import org.roaringbitmap.RoaringBitmap;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.UUID;

/**
 * hash方法计算一个bigfile的去重结果
 *
 * @author chenhonghao
 * @date 2021-03-17 15:18
 */
public class HashFile {

    @Test
    public void test() throws IOException {
        RoaringBitmap roaringBitmap = new RoaringBitmap();
        roaringBitmap.add(100);


        String destFileName = "E:\\file\\output.txt";
        RandomAccessFile destFile = new RandomAccessFile(destFileName, "rw");
        FileChannel destFileChannel = destFile.getChannel();
        while (true){
            String string = UUID.randomUUID().toString();
            destFileChannel.write(ByteBuffer.wrap(string.getBytes()));
        }

    }
}
