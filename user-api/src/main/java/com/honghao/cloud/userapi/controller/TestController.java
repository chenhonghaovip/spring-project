package com.honghao.cloud.userapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.common.enums.RoleTypeEnum;
import com.honghao.cloud.userapi.domain.entity.CloudDeliveryMan;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import com.honghao.cloud.userapi.domain.mapper.master.WaybillBcListMapper;
import com.honghao.cloud.userapi.domain.mapper.slave.CloudOrderMapper;
import com.honghao.cloud.userapi.dto.easypoi.WaybillBcListEasyPoi;
import com.honghao.cloud.userapi.dto.request.*;
import com.honghao.cloud.userapi.facade.WaybillBcListFacade;
import com.honghao.cloud.userapi.service.WaybillBcListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author chenhonghao
 * @date 2019-11-28 15:23
 */
@RestController
@RequestMapping("/testController")
public class TestController {
    @Resource
    private WaybillBcListFacade waybillBcListFacade;
    @Resource
    private WaybillBcListMapper waybillBcListMapper;
    @Resource
    private CloudOrderMapper cloudOrderMapper;

    private static List<Transaction> transactions;
    static {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
        transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }
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

    @GetMapping("/test002")
    public BaseResponse test002(@RequestParam @NotBlank String data){
        JSONObject jsonObject = new JSONObject();


        List<Transaction> list = transactions.stream().filter(each -> each.getYear()==2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        jsonObject.put("list",list);

        List<String> list1 = transactions.stream().map(each ->each.getTrader().getCity()).distinct().collect(Collectors.toList());
        jsonObject.put("list1",list1);

        List<Trader> list2 = transactions.stream()
                .map(Transaction::getTrader)
                .filter(each -> "Cambridge".equals(each.getCity()))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName)).collect(Collectors.toList());
        jsonObject.put("list2",list2);

        List<String> list3 = transactions.stream().map(each -> each.getTrader().getName()).sorted().collect(Collectors.toList());
        jsonObject.put("list3",list3);

        Optional<Trader> optionalTrader = transactions.stream().map(Transaction::getTrader)
                .filter(each -> "Milan".equals(each.getCity())).findAny();
        optionalTrader.ifPresent(System.out::println);

        int sum = transactions.stream()
                .filter(each -> "Cambridge".equals(each.getTrader().getCity()))
                .map(Transaction::getValue).reduce(0,Integer::sum);
        jsonObject.put("sum",sum);

        int sum1 = transactions.stream().mapToInt(Transaction::getValue).sum();
        jsonObject.put("sum1",sum1);

        int max = transactions.stream()
                .map(Transaction::getValue).reduce(0,Integer::max);
        jsonObject.put("max",max);

        Transaction transaction = transactions.stream().min(Comparator.comparing(Transaction::getValue)).orElse(null);
        jsonObject.put("transaction",transaction);

        List<String> list4 = Stream.of("Java 8 ", "Lambdas ", "In ", "Action").map(String::toUpperCase).collect(Collectors.toList());
        jsonObject.put("list4",list4);

        String[] strings = new String[]{"Java 8 ", "Lambdas ", "In ", "Action"};
        String name = Arrays.stream(strings).map(each -> each.split("")).flatMap(Arrays::stream).distinct()
                .reduce("",(a,b)->a+b);

        jsonObject.put("name",name);

        return BaseResponse.successData(jsonObject);

    }

    @GetMapping("/test003")
    public BaseResponse test003(@RequestParam @NotBlank String data){
        JSONObject jsonObject = new JSONObject();
        String[] strings = new String[]{"Java 8 ", "Lambdas ", "In ", "Action"};
        String name = Arrays.stream(strings).map(each -> each.split("")).flatMap(Arrays::stream).distinct()
                .collect(Collectors.joining());
        //对一个交易列表按货币分组，获得该货币的所有交易额总和（返回一个Map<Currency,Integer>
        Map<Integer,List<Transaction>> map = transactions.stream().collect(Collectors.groupingBy(Transaction::getYear));
        jsonObject.put("map",map);

        int sum = transactions.stream().mapToInt(Transaction::getValue).sum();
        jsonObject.put("sum",sum);

        double average = transactions.stream().collect(Collectors.averagingDouble(Transaction::getValue));
        jsonObject.put("average",average);

        String cityName = transactions.stream().filter(each ->"".equals(each.getTrader().getName())).map(each-> each.getTrader().getCity()).
                findAny().orElse("unknow");

        Optional<Person> optionalPerson = Optional.empty();
        Optional<Car> car = optionalPerson.flatMap(Person::getCar);
        return BaseResponse.successData(jsonObject);
    }

    @GetMapping("/test004")
    public BaseResponse test004(@RequestParam String data){
        JSONObject jsonObject = new JSONObject();
        Map<String,Object> map = new HashMap<>();
        map.put("k",new EventDTO());
        Optional<Object> value = Optional.ofNullable(map.get("key"));
        EventDTO eventDTO = (EventDTO) value.orElse(new EventDTO());

        String cityName = transactions.stream().filter(each ->"".equals(each.getTrader().getName())).map(each-> each.getTrader().getCity()).
                findAny().orElse("unknow");
        jsonObject.put("cityName",cityName);
        waybillBcListFacade.test004();
        return BaseResponse.successData(jsonObject);
    }

    @GetMapping("/test005")
    public BaseResponse test005(@RequestParam(required = false) String data){
        List<WaybillBcList> waybillBcLists = waybillBcListMapper.selectAllOrder("2019-12-6 10:27:47",0);
        Map<String,List<WaybillBcList>> map = waybillBcLists.stream().collect(Collectors.groupingBy(WaybillBcList::getUserId));

        List<WaybillBcList> insertList = new ArrayList<>();
        String batchId = "";
        for (List<WaybillBcList> value : map.values()) {
            batchId = "";
            for (WaybillBcList waybillBcList : value) {
                waybillBcList.setWId("");
                waybillBcList.setBatchId(batchId);
            }
            insertList.addAll(value);
        }

//        int i = waybillBcListMapper.insertBatch(insertList);
        return BaseResponse.successData(map);
    }

    @GetMapping("/dataSourceTest")
    public BaseResponse<List<WaybillBcList>> orderList(){
        List<WaybillBcList> lists = waybillBcListMapper.selectAllOrder("2019-12-6 10:27:47",0);
        CloudDeliveryMan cloudDeliveryMan = cloudOrderMapper.selectByPrimaryKey("123");
        return BaseResponse.successData(lists);
    }
}
