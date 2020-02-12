package com.changgou.gateway.web.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class AuthFilter implements GlobalFilter, Ordered {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        //获取请求的URL
        ServerHttpRequest request = exchange.getRequest();
        String url = request.getURI().getPath();

        //1）判断当前请求是否为登录请求，是的话，则放行
        if(url.contains("/api/oauth/login") || !AuthenticationURLConnection.isAuthenticationURL(url)) {
            return chain.filter(exchange);
        }
        //2) 判断cookie中是否存jti信息
        HttpCookie jtiCookie = getJtiCookie(request);
        if(jtiCookie == null) {
           // response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.setStatusCode(HttpStatus.FOUND);
            URI uri = request.getURI();
            response.getHeaders().set("location","http://localhost:9200/login.html?redirect=" + uri);
            return response.setComplete();
        }

        //3)判断Redis中根据jti是否能查找到令牌值
        String jwt =  redisTemplate.boundValueOps(jtiCookie.getValue()).get();
        if(jwt == null) {
           // response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.setStatusCode(HttpStatus.FOUND);
            URI uri = request.getURI();
            response.getHeaders().set("location","http://localhost:9200/login.html?redirect=" + uri);
            return response.setComplete();
        }
        //4)将authorization作为键，将 bearer 令牌作为值 存储到请求头，放行；
        request.mutate().header("authorization", "bearer " + jwt);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private HttpCookie getJtiCookie(ServerHttpRequest request) {
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        for (String name : cookies.keySet()) {
            if(name.equals("jti")) {
                HttpCookie cookie = cookies.getFirst(name);
                return cookie;
            }
        }

        return null;
    }
}
