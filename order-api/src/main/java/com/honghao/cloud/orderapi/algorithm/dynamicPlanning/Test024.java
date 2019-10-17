package com.honghao.cloud.orderapi.algorithm.dynamicPlanning;

/**
 * 动态规划之金字塔
 *
 * @author chenhonghao
 * @date 2019-10-15 17:05
 */
public class Test024 {
    public static void main(String[] args) {
        int[][] arr = new int[][]{{3},{1,5},{8,4,3},{2,6,7,9},{6,2,3,5,1}};
        Test024 test024 = new Test024();

        System.out.println(test024.getMax(arr));
    }

    public int getMax(int[][] arr){
        int max = 0;
        int[][] result = new int[arr.length][arr.length];
        result[0][0] = arr[0][0];

        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j <= i ; j++) {
                if (j==0){
                    result[i][j] = result[i-1][j]+arr[i][j];
                }else {
                    result[i][j] = Math.max(result[i-1][j-1],result[i-1][j])+arr[i][j];
                }
                max = Math.max(result[i][j],max);
            }
        }
        return max;
    }
}
