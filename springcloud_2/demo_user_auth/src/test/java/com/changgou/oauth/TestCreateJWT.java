package com.changgou.oauth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

public class TestCreateJWT {
    @Test
    public void test01() {
        //1.根据秘钥证书 获取私钥
        //1.1 指定秘钥证书位置
        //参数1.秘钥证书位置   秘钥库密码
        /**
         * keytool -genkeypair -alias changgou -keyalg RSA -keypass changgou -keystore changgou.jks -storepass changgou.
         */
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("changgou.jks"), "changgou".toCharArray());

        //1.2从秘钥证书中提取秘钥（公钥，私钥）对
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("changgou", "changgou".toCharArray());
        
        //1.3获取私钥
        PrivateKey privateKey = keyPair.getPrivate();


        //2根据私钥+ 载体---->jwt令牌
        Map<String,Object> payLoad = new HashMap<>();
        payLoad.put("name", "ilove miss cang");
        payLoad.put("age", "20");
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(payLoad), new RsaSigner((RSAPrivateKey) privateKey));
        System.out.println(jwt.getEncoded());

    }
}
