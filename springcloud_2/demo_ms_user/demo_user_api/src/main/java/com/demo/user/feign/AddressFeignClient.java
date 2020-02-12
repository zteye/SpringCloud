package com.demo.user.feign;

import entity.Result;
import com.demo.user.pojo.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("user")
public interface AddressFeignClient {
    @GetMapping("/address/findAddressByUsername")
    public Result<List<Address>> findAddressByUsername();
}
