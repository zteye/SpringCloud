package com.demo.user.feign;

import com.demo.user.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user")
public interface UserFeignClient {
    @GetMapping("/user/findByUsername/{username}")
    public User findByUsername(@PathVariable("username") String username);
}
