package com.demo.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

public class TokenDecode {
    //公钥
    private static final String PUBLIC_KEY = "public.key";

    private static String publickey="";

    /***
     * 获取用户信息
     * @return
     */
    public Map<String,String> getUserInfo(){
        //获取授权信息
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        //令牌解码
        return dcodeToken(details.getTokenValue());
    }

    /***
     * 读取令牌数据
     */
    public Map<String,String> dcodeToken(String token){
        //校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(getPubKey()));

        //获取Jwt原始内容
        String claims = jwt.getClaims();
        return JSON.parseObject(claims,Map.class);
    }


    /**
     * 获取非对称加密公钥 Key
     * @return 公钥 Key
     */
    public String getPubKey() {
        if(!StringUtils.isEmpty(publickey)){
            return publickey;
        }
        Resource resource = new ClassPathResource(PUBLIC_KEY);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            BufferedReader br = new BufferedReader(inputStreamReader);
            publickey = br.lines().collect(Collectors.joining("\n"));
            return publickey;
        } catch (IOException ioe) {
            return null;
        }
    }
    public static void main(String[] args) {
    	TokenDecode tokenDecode = new TokenDecode();
    	String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwibmFtZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTU4MTUzMjYzNywiYXV0aG9yaXRpZXMiOlsic2Vja2lsbF9saXN0IiwiUk9MRV9BRE1JTiIsImdvb2RzX2xpc3QiLCJST0xFX1VTRVIiXSwianRpIjoiNmJiMGY2NjQtOGUwNS00ZDRiLWIwYjQtZTk4MjM1YjA1YmIxIiwiY2xpZW50X2lkIjoiY2hhbmdnb3UiLCJ1c2VybmFtZSI6ImhlaW1hIn0.aeGulDfxqH3ncj5w3OfWle82nqqOHH7tYbbx80PddxuCgTumidoCyVShbjrB2OkVaR6nAtyD7r7D-8b4Tgq6bvrbZcX_0mNv8R0OIjEpyCvmE5veCkilYGMQDytw2NmbCJCfAQJJs70KeUo2TJLvOU9YFWgBOPcRJ5HUjF1qcmvRy6HSuHp4JpPJr482fR24JZJJZqwiJOrLvYdUvJOuzWTd92EiYSvnZlk_O44DpWlRUvh8KHSvYFG9D4ysH7Y-MOevC5dqOQ7T7z_VRk9ULF5_fD-1e4PhhR3QTBkCSmZrRWdA_EvD7x8qkOZxesRpt4qLL-GM1rIQU92WJw5IFQ";
		System.out.println(tokenDecode.dcodeToken(token));
	}
}
