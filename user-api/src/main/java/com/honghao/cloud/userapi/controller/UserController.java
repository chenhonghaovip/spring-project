package com.honghao.cloud.userapi.controller;

import com.alibaba.fastjson.JSON;
import com.honghao.cloud.userapi.aspect.Auth;
import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.domain.entity.CloudDict;
import com.honghao.cloud.userapi.dto.request.CreateUserDTO;
import com.honghao.cloud.userapi.dto.test.Loo;
import com.honghao.cloud.userapi.facade.WaybillBcListFacade;
import com.honghao.cloud.userapi.utils.JedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.geo.GeoRadiusParam;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * 用户信息controller
 *
 * @author chenhonghao
 * @date 2019-07-17 17:22
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api("用户接口服务")
@Validated
public class UserController {
    private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    @Resource
    private WaybillBcListFacade waybillBcListFacade;
    @Resource
    private JedisOperator jedisOperator;

    @Resource
    @Qualifier("spring.redis.pool")
    private JedisPool jedisPool;

    /**
     * 创建用户
     * @param data shuju
     * @return BaseResponse
     */

    @PostMapping("/create")
    @ApiOperation(value = "创建用户",notes = "创建用户")
    public BaseResponse<Boolean> createUser(@RequestBody String data) {
        waybillBcListFacade.createUser(data);
        return BaseResponse.successData(true);
    }

    /**
     * 创建用户
     * @param data data
     * @return BaseResponse
     */
    @Auth
    @PostMapping("/create1")
    @ApiOperation(value = "测试" ,notes = "测试")
    public BaseResponse<String> getUser(@RequestBody String data) {
        waybillBcListFacade.createUser2(data);
        return null;
    }

    /**
     * 创建用户
     * @param kappId 骑士id
     * @return BaseResponse
     */
    @GetMapping("/create2")
    @ApiOperation(value = "测试" ,notes = "测试")
    public BaseResponse<String> create2(@RequestParam("kappId") @Valid @NotBlank String kappId) {
        waybillBcListFacade.createUser2(kappId);
        return null;
    }

    @PostMapping("/test001")
    public BaseResponse test01(@RequestBody @Validated CreateUserDTO createUserDTO){
        System.out.println(JSON.toJSONString(createUserDTO));
        return null;
    }


    @GetMapping("/test002")
    public BaseResponse test02(){
        try {
            long nowTime = System.currentTimeMillis();
            System.out.println(THREAD_LOCAL.get().parse(String.valueOf(nowTime)));
            Date temp = new Date();
            String time = THREAD_LOCAL.get().format(temp);
            System.out.println(time);
            String string = "2018-8-24 12:50:20:545";
            Date date = THREAD_LOCAL.get().parse(string);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            THREAD_LOCAL.remove();
        }
        return null;
    }

    /**
     * geoHash测试
     * @param data data
     * @return BaseResponse
     */
    @PostMapping("/test003")
    public BaseResponse test03(@RequestBody String data){
        System.out.println(data);
        //存放geo信息，存放到redis中为zset结构
        jedisOperator.geoadd("test02",Double.valueOf("121.3717178602589"),Double.valueOf("31.17087293836589"),"33333");
        jedisOperator.geoadd("test02",Double.valueOf("121.3716178602589"),Double.valueOf("31.17087293836589"),"44444");
        jedisOperator.geoadd("test02",Double.valueOf("121.3715178602589"),Double.valueOf("31.17087293836589"),"55555");
        jedisOperator.geoadd("test02",Double.valueOf("121.3719178602589"),Double.valueOf("31.17087293836589"),"11111");
        jedisOperator.geoadd("test02",Double.valueOf("121.3718178602589"),Double.valueOf("31.17087293836589"),"22222");

        //获取半径范围内的所有订单信息（主键值，经纬度，距离信息），并且按照顺序升序排序
        List<GeoRadiusResponse> list1 = jedisOperator.georadius("test02", Double.valueOf("121.3719178602589"), Double.valueOf("31.17087293836589"), 1, GeoUnit.M, GeoRadiusParam.geoRadiusParam().withCoord().withDist().sortDescending());
        for (GeoRadiusResponse geoRadiusResponse : list1) {
            geoRadiusResponse.getMemberByString();
            System.out.println( geoRadiusResponse.getMemberByString() + " distance:" + geoRadiusResponse.getDistance());
        }

        List<GeoRadiusResponse> result = list1.subList(0,Math.min(list1.size(),25));
        String wId;
        double lo,la,dist;
        for (GeoRadiusResponse geoRadiusResponse : result) {
            wId = geoRadiusResponse.getMemberByString();
            lo = geoRadiusResponse.getCoordinate().getLongitude();
            la = geoRadiusResponse.getCoordinate().getLongitude();
            dist = geoRadiusResponse.getDistance();
        }
        //获取这两个key值之间的距离
        Double dis = jedisOperator.geodist("test02","11111","22222",GeoUnit.M);
        System.out.println(dis.doubleValue());


        return BaseResponse.success();
    }


    @PostMapping("/test005")
    public BaseResponse test05(@RequestBody String data){
        Loo test = new Loo("chen","21",new Date(),LocalDate.now(),LocalDateTime.now(),LocalTime.now());
        System.out.println(LocalDateTime.now());

        //LocalDateTime类型转换
        String str = "1986-04-08 12:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        System.out.println(dateTime);
        return BaseResponse.successData(dateTime);
    }

    @PostMapping("/test006")
    public BaseResponse test06(@RequestBody @Validated(CloudDict.Person.class) CloudDict data){
        log.info("main is start");
        CountDownLatch countDownLatch = new CountDownLatch(2);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,4,20, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(10));
        Stream.of(1,2).forEach(each -> threadPoolExecutor.execute(() -> {
                    log.info("test05 start");
                    countDownLatch.countDown();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            test05("111");
                }
        ));

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("main is end");
        return BaseResponse.success();
    }

    @GetMapping("/test007")
    public BaseResponse<Boolean> test007(@RequestParam("wId") String wId){
        Loo loo = new Loo();
        loo.setAge("21");
        Loo lo1 = new Loo();
        lo1.setAge("21");
        boolean flag = Objects.equals(loo,lo1);
        boolean flag1 = Objects.deepEquals(loo,lo1);
        System.out.println(flag);
        return BaseResponse.successData(flag1);
    }

    @GetMapping("/easypoi")
    public BaseResponse easypoi(@RequestParam("wId") @NotBlank String wId){
       return waybillBcListFacade.easypoi();
    }

    @GetMapping("/test0011")
    public List<String> test(){
        Jedis jedis = jedisPool.getResource();
        jedis.lpush("phone","1","2","11","21","12","22","13","23","1","1");
        jedis.lrem("phone",0,"1");
        return null;
    }


}
