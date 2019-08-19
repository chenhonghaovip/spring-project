package com.honghao.cloud.userapi.test;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * 缓存测试
 *
 * @author chenhonghao
 * @date 2019-08-19 09:33
 */
@Slf4j
public class CacheLineEffect {
    static long[][] arr;

    public static void main(String[] args) {

        // 64位CPU的缓存行大小一般是64字节，因此我们每行填充8个long类型
        arr = new long[2 << 20][8];
        Random random = new Random();
        for (int i = 0; i < 2 << 20; i++) {
            arr[i] = new long[8];
            for (int j = 0; j < 8; j++) {
                arr[i][j] = random.nextInt(100);
            }
        }
        long sum = 0L;
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 2 << 20; i += 1) {
            for (int j = 0; j < 8; j++) {
                sum += arr[i][j];
            }
        }
        System.out.println("利用缓存行性质横向遍历时间:" + (System.currentTimeMillis() - begin) + "ms" + "，求和值为 :" + sum);

        sum = 0L;
        begin = System.currentTimeMillis();
        for (int i = 0; i < 8; i += 1) {
            for (int j = 0; j < 2 << 20; j++) {
                sum += arr[j][i];
            }
        }
        System.out.println("不利用缓存行性质纵向遍历时间:" + (System.currentTimeMillis() - begin) + "ms" + "，求和值为 :" + sum);
        /**
         * 结论：横向遍历二位数组时，缓存行会预加载8个long类型的值（理想情况），而纵向遍历则不会用到预加载的数据
         * ，利用缓存行的性质遍历数组比起不利用缓存行的性质遍历数组要时间要少的多。
         */
    }
}
