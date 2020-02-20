package com.changgou.oauth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ecwid.consul.v1.ConsulClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsulTest {
	
	@Autowired
	ConsulClient consulClient;
	
	@Test
	public void testConsul() {
		String path = System.getenv("Path");
		
		consulClient.setKVValue("config/test", path);
	}

}
