package com.study.nacos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
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

    //通过服务名从nacos中发现实例，再通过负载均衡算法去获取实例
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
}
