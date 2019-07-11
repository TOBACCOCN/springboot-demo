package com.springboot.example.util;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class SimpleX509TrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType){
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        // 不能返回 null，会导致 okHttpClient 执行 https 请求抛空指针异常
        return new X509Certificate[]{};
    }
}