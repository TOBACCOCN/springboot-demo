package com.springboot.example.util;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * OkHttp 工具类
 *
 * @author zhangyonghong
 * @date 2019.6.14
 */
public class OkHttpUtil {

    private static Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);

    /**
     * GET 请求
     *
     * @param url 请求地址
     * @return 响应消息
     */
    public static String httpGet(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        // 1.发送同步请求
        try {
            Response response = call.execute();
            return response.body() == null ? "" : response.body().string();
        } catch (IOException e) {
            ErrorPrintUtilTests.printErrorMsg(logger, e);
            return "";
        }
        // 2.发送异步请求，适合在 android 上使用
        // SimpleCallback simpleCallback = new SimpleCallback();
        // call.enqueue(simpleCallback);
        // return "";
    }

    /**
     * POST 请求
     *
     * @param url         请求地址
     * @param headerMap   请求头参数
     * @param contentType 请求头 contentType
     * @param param       请求体参数
     * @return 响应消息
     */
    public static String httpPost(String url, Map<String, String> headerMap, String contentType, String param) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Headers.Builder headersBuilder = new Headers.Builder();
        headerMap.forEach(headersBuilder::add);
        RequestBody requestBody = RequestBody.create(MediaType.parse(contentType), param);
        Request request = new Request.Builder()
                .url(url)
                .headers(headersBuilder.build())
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response.body() == null ? "" : response.body().string();
        } catch (IOException e) {
            ErrorPrintUtilTests.printErrorMsg(logger, e);
            return "";
        }
    }

    @Test
    public void httpGet() {
        String url = "https://www.baidu.com";
        String result = httpGet(url);
        logger.info(">>>>> RESULT: {}", result);
    }

    @Test
    public void httpPost() {
        String url = "http://127.0.0.1:9527/hello";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", UUID.randomUUID().toString().replaceAll("=", ""));
        String contentType = "application/x-www-form-urlencoded";
        String param = "foo=bar&bar=barz";
        String result = httpPost(url, headerMap, contentType, param);
        logger.info(">>>>> RESULT: {}", result);
    }

    @Test
    public void httpPostJSON() {
        String url = "http://127.0.0.1:9527/helloJSON";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", UUID.randomUUID().toString().replaceAll("=", ""));
        String contentType = "application/json";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("foo", "bar");
        paramMap.put("bar", "barz");
        String param = JSON.toJSONString(paramMap);
        String result = httpPost(url, headerMap, contentType, param);
        logger.info(">>>>> RESULT: {}", result);
    }

}
