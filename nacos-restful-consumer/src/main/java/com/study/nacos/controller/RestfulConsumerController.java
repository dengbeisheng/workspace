package com.study.nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

}
