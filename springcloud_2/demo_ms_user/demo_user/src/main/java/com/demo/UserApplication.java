package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.demo.user.dao"})
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class UserApplication {

	public static void main(String[] args) {
		  SpringApplication.run( UserApplication.class);
	}

}
