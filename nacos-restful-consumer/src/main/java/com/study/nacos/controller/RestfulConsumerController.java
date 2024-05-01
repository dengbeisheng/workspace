package com.study.nacos.controller;

import com.study.microservice.service1.api.Service1Api;
import com.study.microservice.service2.api.Service2Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author dengbeisheng
 */
@RestController
public class RestfulConsumerController {

    @Value("${provider.address}")
    private String providerAddress;

    @GetMapping(value = "/service")
    public String getService(){
        String restAddress = "http://" + providerAddress + "/service";
        System.out.println(providerAddress);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(restAddress, String.class);
        return "consumer invoke | " + result;
    }

    /**
     * 通过服务名从nacos中发现实例，再通过负载均衡算法去获取实例
     */
    private String serviceName = "nacos-restful-provider";
    @Autowired
    LoadBalancerClient loadBalancerClient;
    @GetMapping(value = "/nacosService")
    public String getNacosService(){
        ServiceInstance choose = loadBalancerClient.choose(serviceName);
        URI uri = choose.getUri();
        String restAddress = uri + "/service";
        System.out.println(providerAddress);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(restAddress, String.class);
        return "nacos consumer invoke | " + result;
    }

    /**
     * application应用层通过dubbo进行远程调用service2
     */
    @org.apache.dubbo.config.annotation.Reference
    Service2Api service2Api;
    @GetMapping(value = "/dubboService2")
    public String getDubboService2(){
        String result = service2Api.dubboService2Api();
        return "dubbo consumer invoke | " + result;
    }

    /**
     * application应用层通过dubbo进行远程调用service1
     */
    @org.apache.dubbo.config.annotation.Reference
    Service1Api service1Api;
    @GetMapping(value = "/dubboService1")
    public String getDubboService1(){
        String result = service1Api.dubboService1Api();
        return "dubbo consumer invoke | " + result;
    }

    /**
     * nacos配置中心获取配置
     */
    @Value("${common.name}")
    private String common_name;
    //ConfigurableApplicationContext 动态获取配置中心参数
    @Autowired
    ConfigurableApplicationContext applicationContext;
    @GetMapping(value = "/nacosConfig")
    public String getNacosConfig(){
        String result = applicationContext.getEnvironment().getProperty("common.name");
        return "nacos config consumer invoke | " + common_name + " || " + result;
    }
}
