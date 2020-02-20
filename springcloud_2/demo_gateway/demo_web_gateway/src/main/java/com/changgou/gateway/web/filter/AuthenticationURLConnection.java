package com.changgou.gateway.web.filter;

import java.util.Arrays;
import java.util.List;

public class AuthenticationURLConnection {

    public static boolean isAuthenticationURL(String url) {
        ///api/order/batchSend
        String urlsStr = "/api/wseckillorder,/api/wxpay,/api/wxpay/**,/api/worder/**,/api/us er/**,/api/address/**,/api/wcart/**,/api/cart/**,/api/categoryReport/**,/api/ord erConfig/**,/api/order/**,/api/orderItem/**,/api/orderLog/**,/api/preferential/* *,/api/returnCause/**,/api/returnOrder/**,/api/returnOrderItem/**,/api/seckillorder/**,/uorder/findAllOrders,/uorder/**,/actuator/**";
        String[] urls = urlsStr.replace("/**", "").split(",");
        for (String s : urls) {
            if(url.contains(s)) {
                return true;
            }
        }
        return false;
    }
}
