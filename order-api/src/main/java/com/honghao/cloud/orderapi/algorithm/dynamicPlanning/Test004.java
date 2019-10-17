package com.honghao.cloud.orderapi.algorithm.dynamicPlanning;

/**
 * 路径最短
 *
 * @author chenhonghao
 * @date 2019-10-17 13:19
 */
public class Test004 {
    public static void main(String[] args) {
        int[][] arr = new int[][]{
                {1, 3 ,5, 9},
                {8 ,1 ,3 ,4},
                {5 ,0 ,6 ,1},
                {8, 8, 4, 0}
        };
        Test004 test004 = new Test004();
        System.out.println(test004.getMin(arr));

    }
    public int getMin(int[][] arr){
        int min = 0;
        int[][] temp = new int[arr.length][arr[0].length];

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (i == 0 && j == 0){
                    temp[i][j] = arr[i][j];
                }else if (i ==0 && j!=0){
                    temp[i][j] = arr[i][j]+ temp[i][j-1];
                }else if (i!=0 && j==0){
                    temp[i][j] = arr[i][j]+ temp[i-1][j];
                }else {
                    temp[i][j] = arr[i][j]+ Math.min(temp[i-1][j],temp[i][j-1]);
                }
            }
        }
        return temp[arr.length-1][arr.length-1];
    }
}
