package com.honghao.cloud.userapi.controller;

import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.dto.easypoi.WaybillBcListEasyPoi;
import com.honghao.cloud.userapi.service.WaybillBcListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
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
        List<Integer> list = Arrays.asList(1,3,5,8,9);
        list = list.stream().filter(each -> each < 4).collect(Collectors.toList());
        System.out.println(Arrays.toString(list.toArray()));

        List<WaybillBcListEasyPoi> waybillBcLists = waybillBcListService.selectOrders();
        List<WaybillBcListEasyPoi> result = filterApples(waybillBcLists,WaybillBcListEasyPoi::isBatchId);

        File[] hiddenFiles = new File(".").listFiles(File::isHidden);
        return BaseResponse.successData(hiddenFiles);
    }

   private static List<WaybillBcListEasyPoi> filterApples(List<WaybillBcListEasyPoi> inventory,
                                            Predicate<WaybillBcListEasyPoi> p) {
        List<WaybillBcListEasyPoi> result = new ArrayList<>();
        for (WaybillBcListEasyPoi apple: inventory){
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }
}
