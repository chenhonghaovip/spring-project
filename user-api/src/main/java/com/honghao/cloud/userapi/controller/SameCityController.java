package com.honghao.cloud.userapi.controller;

import com.honghao.cloud.userapi.base.BaseResponse;
import com.honghao.cloud.userapi.domain.entity.WaybillBcList;
import com.honghao.cloud.userapi.dto.response.SameCityNumVO;
import com.honghao.cloud.userapi.facade.SameCitySearchFacade;
import com.honghao.cloud.userapi.facade.SameCityServiceFacade;
import com.honghao.cloud.userapi.timer.PrintWordsJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 同城订单控制
 *
 * restful通过在url资源路径中添加版本号来解决版本不兼容问题
 * @author chenhonghao
 * @date 2019-12-26 10:37
 */
@Slf4j
@RestController
@RequestMapping("/sameCity")
public class SameCityController {
    @Resource
    private SameCitySearchFacade sameCitySearchFacade;
    @Resource
    private SameCityServiceFacade sameCityServiceFacade;
    @Resource
    private Scheduler scheduler;

    @PostConstruct
    public void init(){
        try {
            scheduler.start();
            System.out.println("111111111111111111111111111");
            System.out.println("333333333333333333333");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    /**
     * 查询同城订单数量
     * @param knightId 骑士id
     * @return BaseResponse
     */
    @GetMapping("/getNum")
    public BaseResponse<List<SameCityNumVO>> getNum(@RequestParam("knightId") String knightId){
        return BaseResponse.successData(sameCitySearchFacade.getNum(knightId));
    }

    @PostMapping("/receive")
    public BaseResponse receive(@RequestBody @Valid WaybillBcList waybillBcList){
        sameCityServiceFacade.receive("chenhonghoa");
        return BaseResponse.success();
    }

    @GetMapping("/schedulerStart")
    public BaseResponse schedulerStart(@RequestParam("name") String name,@RequestParam("group") String group){
        Map<String,String> map = new HashMap<>();
        map.put("name",name);
        map.put("group",group);
        JobDetail jobDetail = JobBuilder.newJob(PrintWordsJob.class)
                .setJobData(new JobDataMap(map))
                .withIdentity(name, group).build();

        // 3、构建Trigger实例,每隔1s执行一次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
                .startNow()//立即生效
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(1)
                        .withRepeatCount(3)).build();

        try {
            scheduler.scheduleJob(jobDetail,trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return BaseResponse.success();
    }

    @GetMapping("/schedulerEnd")
    public BaseResponse schedulerEnd(@RequestParam("name") String name,@RequestParam("group") String group){
        try {
            scheduler.deleteJob(new JobKey(name,group));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return BaseResponse.success();
    }
}
