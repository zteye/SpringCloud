package com.changgou.oauth;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class TestBcript {
    public static void main(String[] args) {
        String gensalt = BCrypt.gensalt();
        System.out.println(BCrypt.hashpw("123", gensalt));
    }
}
