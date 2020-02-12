package com.changgou.oauth.controller;

import entity.Result;
import entity.StatusCode;
import com.changgou.oauth.service.AuthService;
import com.changgou.oauth.util.AuthToken;
import com.changgou.oauth.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/oauth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Value("${auth.clientId}")
    private String clientId;
    @Value("${auth.clientSecret}")
    private String clientSecret;

    /**
     * auth:
     *   ttl: 3600  #token存储到redis的过期时间
     *   clientId: changgou
     *   clientSecret: changgou
     *   cookieDomain: localhost
     *   cookieMaxAge: -1
     */
    @Value("${auth.cookieDomain}")
    private String cookieDomain;

    @Value("${auth.cookieMaxAge}")
    private int  cookieMaxAge;



    @PostMapping("/login")
    public Result login(String username, String password, HttpServletResponse response) {
        AuthToken authToken = authService.login(username, password, clientId, clientSecret);
        if(authToken == null) {
            return new Result(false, StatusCode.ERROR, "登录失败");
        }
        //将jti存储到Cookie中；
        CookieUtil.addCookie(response,cookieDomain ,"/" ,"jti" , authToken.getJti(),cookieMaxAge ,false );
        return new Result(true, StatusCode.OK, "登录成功");
    }
}
