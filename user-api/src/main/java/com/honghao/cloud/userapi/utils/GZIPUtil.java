package com.honghao.cloud.userapi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 压缩工具类
 *
 * @author chenhonghao
 * @date 2019-11-12 16:47
 */
public class GZIPUtil {
    private static final Logger logger = LoggerFactory.getLogger(GZIPUtil.class);
    /**
     * 压缩的字符串编码
     */
    private static final String ENCODING = "ISO-8859-1";
    /**
     * 压缩字符串
     * @param data 需要压缩的字符串
     * @return 返回压缩后的字符串
     * @throws IOException io异常
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    public static String compress(String data)throws IOException {
        if (data == null || data.length() == 0) {
            return data;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(data.getBytes());
        gzip.finish();
        gzip.flush();
        gzip.close();
        return out.toString(ENCODING);
    }

    /**
     * 压缩字符串
     * @param data 需要压缩的字符串
     * @return 返回压缩后的字符串
     * @throws IOException io异常
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    public static String[] compressBatch(String[] data)throws IOException {
        if (data == null) {
            return data;
        }
        for (int i = 0; i < data.length; i++) {
            String datum = data[i];
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(datum.getBytes());
            gzip.finish();
            gzip.flush();
            gzip.close();
            data[i] = out.toString(ENCODING);
        }
        return data;
    }

    /**
     * 解压缩字符串
     * @param data 需要解压的字符串
     * @return 解压缩后的字符串
     * @throws IOException io异常
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    public static String uncompress(String data) throws IOException {
        if (data == null || data.length() == 0) {
            return data;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes(ENCODING));
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        // toString()使用平台默认编码，也可以显式的指定如toString("GBK";)
        return out.toString();
    }


    /**
     * 解压缩字符串
     * @param data 需要解压的字符串
     * @return 解压缩后的字符串
     * @throws IOException io异常
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    public static String[] unCompressBatch(String[] data) throws IOException {
        if (data == null) {
            return data;
        }
        for (int i = 0; i < data.length; i++) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(data[i].getBytes(ENCODING));
            GZIPInputStream gunzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gunzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            data[i] = in.toString();
            // toString()使用平台默认编码，也可以显式的指定如toString("GBK";)
        }

        return data;
    }
}
