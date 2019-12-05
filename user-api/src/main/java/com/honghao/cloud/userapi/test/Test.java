package com.honghao.cloud.userapi.test;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author chenhonghao
 * @date 2019-07-17 09:23
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
//        //数字字符串
//        String name="1048576.1024";
//        //构造以字符串内容为值的BigDecimal类型的变量bd
//        BigDecimal bd=new BigDecimal(name);
//        //设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
//        bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP);
//        //转化为字符串输出
//        System.out.println(bd);
//        String queues="[{\"queue\":\"user_push_queue\"},{\"queue\":\"delay_ten_min_death\"}]";
//        JSONArray jsonArray= JSON.parseArray(queues);
//        List<JSONObject> list = jsonArray.toJavaList(JSONObject.class);
//        List<String> values = list.stream().map(each->each.getString("queue")).collect(Collectors.toList());
//        log.info(String.valueOf(values));
        List<String> tempList = Arrays.asList("11","23","22","33");
        List<String> maxList = new ArrayList<>(tempList);

        List<String> minList = Collections.singletonList("11");
        maxList.removeAll(minList);
        System.out.println(JSON.toJSONString(maxList));
        int n=10;
        Test test = new Test();
        int sum = test.getStep(n);
        log.info("方法有：{}",sum);

    }
    private int getStep(int k){
        if (k == 1 || k==2){
            return 1;
        }else {
            return getStep(k-1)+getStep(k-2);
        }

    }
}
