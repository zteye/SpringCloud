package com.zteye.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "consul-provider")
public interface  ConsulClientFeign {

	@GetMapping(value = "/hi")
    String sayHiFromClientEureka(@RequestParam(value = "name") String name);
}
