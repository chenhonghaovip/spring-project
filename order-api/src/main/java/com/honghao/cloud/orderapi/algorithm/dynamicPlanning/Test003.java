package com.honghao.cloud.orderapi.algorithm.dynamicPlanning;

import lombok.extern.slf4j.Slf4j;

/**
 * 背包问题
 *在N件物品取出若干件放在容量为W的背包里，每件物品的体积为W1，W2……Wn（Wi为整数），与之相对应的价值为P1,P2……Pn（Pi为整数），求背包能够容纳的最大价值。
 *
 * @author chenhonghao
 * @date 2019-10-16 10:41
 */
@Slf4j
public class Test003 {
    public static void main(String[] args) {
        //商品的体积2、3、4、5
        int[] w = { 0, 2 , 3 , 4 , 5 };
        //商品的价值3、4、5、6
        int[] v = { 0, 3 , 4 , 5 , 6 };
        //背包大小
        int bagV = 9;
        //动态规划表
        System.out.println(PackageHelper(4,w,v,bagV));


    }

    public static int PackageHelper(int n,int w[],int p[],int val) {
        int[][] dp = new int[n+1][val+1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= val; j++) {
                if (j < w[i]) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - w[i]] + p[i]);
                }
            }
        }
        return dp[n][val];
    }
}
