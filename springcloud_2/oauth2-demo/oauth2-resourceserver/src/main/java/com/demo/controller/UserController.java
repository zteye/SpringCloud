package com.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	@RequestMapping("/user/{userId}")
	public Object userInfo(@PathVariable  String userId) {
		Map<String, String> userInfo = new HashMap<String, String>();
		userInfo.put("userName", "Jack");
		userInfo.put("age", "20");
		return userInfo;
	}

}
