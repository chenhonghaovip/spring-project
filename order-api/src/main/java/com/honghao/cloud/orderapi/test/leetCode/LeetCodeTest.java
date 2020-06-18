package com.honghao.cloud.orderapi.test.leetCode;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenhonghao
 * @date 2019-07-17 09:23
 */
@Slf4j
public class LeetCodeTest {

    @Test
    public void twoSum(){
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;
        System.out.println(Arrays.toString(twoSum(nums,target)));

    }

    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int except = target - nums[i];
            if (!map.containsKey(except)){
                map.put(nums[i],i);
            }else {
                return new int[]{map.get(except),i};
            }
        }
        return new int[]{};
    }
}
