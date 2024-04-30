package com.study.nacos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dengbeisheng
 */
@RestController
public class RestfulProviderController {
    @GetMapping(value = "/service")
    public String getService(){
        return "provider invoke";
    }
}
