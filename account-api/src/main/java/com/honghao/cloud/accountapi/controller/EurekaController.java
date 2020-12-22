package com.honghao.cloud.accountapi.controller;

import com.honghao.cloud.basic.common.base.BaseResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * eureka测试
 *
 * @author chenhonghao
 * @date 2020-09-04 14:22
 */
@Slf4j
@RestController
@RequestMapping("/eurekaController")
@Api(value = "eureka服务", tags = "eureka服务")
public class EurekaController {
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private LoadBalancerClient loadBalancerClient;

    @Resource
    private DiscoveryClient discoveryClient;


    @PostMapping("/test")
    public BaseResponse test() {
        List<ServiceInstance> instances1 = discoveryClient.getInstances("account-api");
        ServiceInstance choose = loadBalancerClient.choose("user-api");
        List<ServiceInstance> instances = applicationContext.getBean(EurekaDiscoveryClient.class).getInstances("user-api");
        return BaseResponse.successData(instances1);
    }
}
