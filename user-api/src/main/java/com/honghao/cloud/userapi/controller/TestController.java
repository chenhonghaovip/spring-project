package com.honghao.cloud.userapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.common.enums.RoleTypeEnum;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import com.honghao.cloud.userapi.domain.mapper.master.WaybillBcListMapper;
import com.honghao.cloud.userapi.dto.easypoi.WaybillBcListEasyPoi;
import com.honghao.cloud.userapi.dto.request.*;
import com.honghao.cloud.userapi.facade.BatchFacade;
import com.honghao.cloud.userapi.facade.WaybillBcListFacade;
import com.honghao.cloud.userapi.factory.ExecutorFactory;
import com.honghao.cloud.userapi.listener.rabbitmq.producer.MessageSender;
import com.honghao.cloud.userapi.service.WaybillBcListService;
import com.honghao.cloud.userapi.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * java8
 * @author chenhonghao
 * @date 2019-11-28 15:23
 */
@Slf4j
@RestController
@RequestMapping("/testController")
public class TestController {
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1000,1200,20, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
    @Resource
    private WaybillBcListFacade waybillBcListFacade;
    @Resource
    private WaybillBcListMapper waybillBcListMapper;
    @Resource
    private BatchFacade batchFacade;
    @Resource
    private MessageSender messageSender;
//    @Resource
//    private Redisson redisson;
    @Resource
    private WaybillBcListService waybillBcListService;

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
//    public void tesw(){
//        RLock lock = redisson.getLock("");
//        try {
//            lock.tryLock(1,2,TimeUnit.SECONDS);
//        }catch (Exception e){
//            log.error(e.getMessage());
//        }finally {
//            lock.unlock();
//        }
//
//
//    }
    @GetMapping("/test0001")
    public BaseResponse test0001(@RequestParam @NotBlank String data){
        messageSender.testQueue(data);
        return BaseResponse.success();
    }

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
        Map<String,Object> map = new HashMap<>(8);
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
        return BaseResponse.successData(map);
    }

    @GetMapping("/dataSourceTest")
    public BaseResponse orderList(){
        return waybillBcListFacade.dateSource();
    }

    @GetMapping("/reflexTest")
    public BaseResponse reflexTest(){
        return waybillBcListFacade.reflexTest();
    }

    @GetMapping("/selectInfo")
    public void select(){
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            threadPoolExecutor.execute(() -> {
                try {
                    countDownLatch.await();
                    BaseResponse<WaybillBcList> response = batchFacade.queryCommon(String.valueOf(finalI));
                    System.out.println("请求结果为"+response.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            countDownLatch.countDown();
        }
    }

    @Test
    public void test1() {
        for (int i = 0; i < 1000; i++) {
//            test(i);
        }
    }


    @Test
    public void test() {
        ThreadPoolExecutor threadPool = ExecutorFactory.buildThreadPoolExecutor(1,10,"odoofd");
        CyclicBarrier cyclicBarrier = new CyclicBarrier(1000);

        for (int i = 0; i < 2000; i++) {
            System.out.println(i);
            threadPool.submit(()-> deliveryTest(cyclicBarrier));
        }
    }

    private void deliveryTest(CyclicBarrier cyclicBarrier){
        try {
            cyclicBarrier.await();
            String phone = "18234089492";
            String url = "http://api.k780.com:88/?app=phone.get&phone="+phone+"&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=xml";
        } catch (Exception e) {
            e.printStackTrace();
        }

                System.out.println(Thread.currentThread().getName()+"子线程获取结果");
    }

    /**
     * 1.包裹TtlExecutors
     *
     * 2.使用TransmittableThreadLocal
     */
    @Test
    public void testInfo(){
        TransmittableThreadLocal<Integer> ttl = new TransmittableThreadLocal<>();
        ThreadPoolExecutor threadPool = ExecutorFactory.buildThreadPoolExecutor(1,1,"odoofd");
        ExecutorService ttlExecutorService = TtlExecutors.getTtlExecutorService(threadPool);
        ttl.set(1);
        ttlExecutorService.submit(()->{
            System.out.println("第一次"+Thread.currentThread().getName()+"value:"+ttl.get());
            ttl.remove();
        });

        ttl.set(3);

        ttlExecutorService.submit(()->{
            System.out.println("第二次"+Thread.currentThread().getName()+"value:"+ttl.get());
        });
    }

    /**
     * @author YK
     * @date:2017-03-29 上午9:54
     */
    public static class HttpReptileUtils {

        /**
         * @param args
         */
        public static void main(String[] args) {
            String url = "http://mobsec-dianhua.baidu.com/dianhua_api/open/location?tel=17810252046";
            String json = loadJSON(url);
            System.out.println(json);
        }

        public static String loadJSON (String url) {
            String s = HttpUtil.doPost(url, 10);
            return s;
        }
    }
}
