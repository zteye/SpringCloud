package com.changgou.oauth;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

public class TestParserJWT {
    @Test
    public void test01() throws IOException {

        //IOUtils读取公钥配置文件，并且转化为字符串；
        String publicKey = IOUtils.toString(new ClassPathResource("public.key").getInputStream());

        Jwt jwt = JwtHelper.decodeAndVerify("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiaWxvdmUgbWlzcyBjYW5nIiwiYWdlIjoiMjAifQ." +
                "jgxVotmIfTkxKr6zhykyS0JHjBeAAzz1P31dNpO4PbPs4_ti3HUWMDond_7_Wjf3qRfX89VYW11iPaKe" +
                "eVqUPPMguqp_uKfyFYrqY3pukc9ltCKOLIJhqjuGiUjU8FW5NTkDPpNcpQRIqJoN6jS8-qrDifIqi01DAJ7QeXVZ" +
                "sMIBMiMrGB4YuCneLzoTmmH88f-C7_YiscAuZ6atoKzorH8e0-_iMKmEa9DMK_fH0Asg88p1HiFKF4I1hiiOOa" +
                "yb6BjCDGZXV7zHui7XJ1Ampai6-lDX376kKtm0xVTzieTnj-c3WHsI6PlV8Jbx4aq4ImuBHGo88hraRsQxeaH3zw", new RsaVerifier(publicKey));


        String claims = jwt.getClaims();
        System.out.println(claims);
    }
}
