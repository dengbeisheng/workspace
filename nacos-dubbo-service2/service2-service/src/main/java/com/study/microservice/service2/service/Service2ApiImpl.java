package com.study.microservice.service2.service;

import com.study.microservice.service2.api.Service2Api;


/**
 * @author dengbeisheng
 */
@org.apache.dubbo.config.annotation.Service
public class Service2ApiImpl implements Service2Api {
    public String dubboService2Api() {
        return "dubbo service2 invoke";
    }
}
