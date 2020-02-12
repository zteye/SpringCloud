package com.changgou.oauth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGetJwtToken {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Test
    public void test01() {
        //1.1定义请求的地址
        //可能认证服务搭建集群，通过Ribbon负载均衡器，根据微服务的名称获取请求url
        URI uri = loadBalancerClient.choose("user-auth").getUri();
        String url = uri + "/oauth/token";

        //1.2设置请求方式
        //1.3设置请求消息
        //1.3.1 请求体（参数）


        MultiValueMap<String,String> body = new LinkedMultiValueMap<>();
        body.add("grant_type","password");
        body.add("username","itheima");
        body.add("password","itheima");

        //1.3.2 请求头
        MultiValueMap<String,String> multiValueMap = new LinkedMultiValueMap<>();
        String value = "changgou:changgou1";
        value = Base64Utils.encodeToString(value.getBytes());

        multiValueMap.add("Authorization","Basic "+ value );
        HttpEntity httpEntity = new HttpEntity(body,multiValueMap);

        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if(response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
                    super.handleError(response);
                }
            }
        });
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);

        System.out.println(responseEntity.getBody());



    }


}
