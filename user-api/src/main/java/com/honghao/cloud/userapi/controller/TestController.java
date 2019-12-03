package com.honghao.cloud.userapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.common.enums.RoleTypeEnum;
import com.honghao.cloud.userapi.dto.easypoi.WaybillBcListEasyPoi;
import com.honghao.cloud.userapi.service.WaybillBcListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author chenhonghao
 * @date 2019-11-28 15:23
 */
@RestController
@RequestMapping("/testController")
public class TestController {
    @Resource
    private WaybillBcListService waybillBcListService;

    @GetMapping("/test001")
    public BaseResponse test001(@RequestParam @NotBlank String data){
        JSONObject jsonObject = new JSONObject();
        List<Integer> list = Arrays.asList(1,3,5,8,9);
        list = list.stream().filter(each -> each < 4).collect(Collectors.toList());
        System.out.println(Arrays.toString(list.toArray()));

        //过滤filter的使用
        List<WaybillBcListEasyPoi> waybillBcLists = waybillBcListService.selectOrders();
        List<String> list1 = waybillBcLists.stream().filter(each ->
            Integer.valueOf(0).equals(each.getOrderType())
        ).map(WaybillBcListEasyPoi::getBatchId).collect(Collectors.toList());
        jsonObject.put("list1",list1);
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers = numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .skip(3)
                .collect(Collectors.toList());
        jsonObject.put("numbers",numbers);
        String[] s = {"hello","world"};
        List<String> list2 = Arrays.stream(s).
                map(each -> each.split(""))
                .flatMap(Arrays::stream)
                .distinct().collect(Collectors.toList());
        jsonObject.put("list2",list2);
        List<Integer> list3 = Arrays.asList(1, 2, 3, 4, 5);
        list3 = list3.stream().map(i -> i*i).collect(Collectors.toList());

        List<Integer> test01 = Arrays.asList(1,2,3);
        List<Integer> test02 = Arrays.asList(3,4);
        List<int[]> result = test01.stream().flatMap(i->test02.stream().map(j->new int[]{i,j})).collect(Collectors.toList());
        jsonObject.put("result",result);
        List<int[]> result01 = test01.stream().flatMap(i->test02.stream().filter(j->(j+i)%3 == 0).map(j->new int[]{i,j})).collect(Collectors.toList());
        jsonObject.put("result01",result01);
        Optional<WaybillBcListEasyPoi> dish = waybillBcLists.stream().filter(each ->"".equals(each.getBatchId())).findAny();
        if (dish.isPresent()){
            String wId = dish.get().getWId();
            jsonObject.put("wId",wId);
        }else {
            System.out.println("结果不存在");
        }
        String name = RoleTypeEnum.formCode(1).getDesc();
        jsonObject.put("name",name);
        int sum = list3.stream().reduce(0, Integer::sum);
        jsonObject.put("sum",sum);
        int max = list3.stream().reduce(0, Integer::max);
        jsonObject.put("max",max);
        return BaseResponse.successData(jsonObject);
    }

}
