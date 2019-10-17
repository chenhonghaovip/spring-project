package com.honghao.cloud.orderapi.algorithm.dynamicPlanning;

/**
 * 动态规划之金子塔从下到上
 *3                         
 *
 * 1    5                  
 *
 * 8    4    3              
 *
 * 2    6    7    9         
 *
 * 6    2    3    5    1  
 * @author chenhonghao
 * @date 2019-10-15 17:23
 */
public class Test001 {
    public static void main(String[] args) {
        int[][] arr = new int[][]{{3},{1,5},{8,4,3},{2,6,7,9},{6,2,3,5,1}};
        Test001 test024 = new Test001();
        System.out.println(test024.getMax(arr));
    }
    public int getMax(int[][] arr){
        int max = 0;
        int[] temp = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            temp[i] = arr[arr.length-1][i];
        }
        for (int i = arr.length-2; i >= 0 ; i--) {
            for (int j = 0; j <= i; j++) {
                temp[j] =arr[i][j] + Math.max(temp[j],temp[j+1]);
            }
        }
        max = temp[0];
        return max;
    }
}
