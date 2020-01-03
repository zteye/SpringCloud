package com.zteye.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class HiService {

	@Autowired
	ConsulClientFeign client;
	
	@HystrixCommand(fallbackMethod = "defaultFallBack")
	public String sayHi(String name){
        return  client.sayHiFromClientEureka(name);
    }
	
	public String defaultFallBack(String name) {
		return "hystrix opened."+name;
	}
}
