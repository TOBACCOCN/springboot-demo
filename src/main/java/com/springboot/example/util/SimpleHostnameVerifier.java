package com.springboot.example.util;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * 域名合法性验证器
 *
 * @author zhangyonghong
 * @date 2019.6.14
 */
public class SimpleHostnameVerifier implements HostnameVerifier {

    @Override
    public boolean verify(String arg0, SSLSession arg1) {
        return true;
    }

}
