package com.study.microservice.service1.service;

import com.study.microservice.service1.api.Service1Api;
import com.study.microservice.service2.api.Service2Api;

/**
 * @author dengbeisheng
 */
@org.apache.dubbo.config.annotation.Service
public class Service1ApiImpl implements Service1Api {
    @org.apache.dubbo.config.annotation.Reference
    Service2Api service2Api;
    /**
     * service1微服务通过dubbo协议远程调用service2
     * @return
     */
    public String dubboService1Api() {
        String result = service2Api.dubboService2Api();
        return "dubbo service1 invoke | " + result ;
    }
}
