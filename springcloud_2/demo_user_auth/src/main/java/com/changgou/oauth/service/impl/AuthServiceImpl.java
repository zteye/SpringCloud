package com.changgou.oauth.service.impl;

import com.changgou.oauth.service.AuthService;
import com.changgou.oauth.util.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Value("${auth.ttl}")
    private long ttl;

    @Override
    public AuthToken login(String username, String password, String clientId, String clientSecret) {
        //一.根据用户密码，客户端id，客户端密码获取JWT令牌
        //1.1构建请求的url
        URI uri = loadBalancerClient.choose("USER-AUTH").getUri();
        String url = uri + "/oauth/token";
        //1.2构建请求参数
        MultiValueMap<String,String> body =  new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", password);
        //1.3构建请求头
        MultiValueMap<String,String> headers =  new LinkedMultiValueMap<>();
        headers.add("authorization", getHttpBasic(clientId,clientSecret));
        //400 401不抛异常
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
                if(response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
                    super.handleError(url, method, response);
                }
            }
        });
        //1.4发送请求
        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers), Map.class);

        //1.5解析令牌结果
        Map<String,Object> entityBody = responseEntity.getBody();
        if(entityBody != null) {
            AuthToken authToken = new AuthToken();
            authToken.setAccessToken((String) entityBody.get("access_token"));
            authToken.setJti((String) entityBody.get("jti"));
            authToken.setRefreshToken((String) entityBody.get("refresh_token"));

            //二、将jti作为key 将令牌作为值，存储到Redis,设置过期时间
            redisTemplate.boundValueOps(authToken.getJti()).set(authToken.getAccessToken(), ttl, TimeUnit.SECONDS);
            return authToken;
        }

        return null;
    }

    private String getHttpBasic(String clientId, String clientSecret) {
        String value = clientId +":"+clientSecret;
        String encode = Base64Utils.encodeToString(value.getBytes());
        return "Basic " +encode;
    }
}
