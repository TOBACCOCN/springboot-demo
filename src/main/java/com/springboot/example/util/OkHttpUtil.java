package com.springboot.example.util;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

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
        return getResponse(okHttpClient, request);
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
        return getResponse(okHttpClient, request);
    }

    /**
     * 执行请求并返回响应消息
     *
     * @param okHttpClient 请求客户端
     * @param request      请求对象
     * @return 响应消息
     */
    private static String getResponse(OkHttpClient okHttpClient, Request request) {
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response.body() == null ? "" : response.body().string();
        } catch (IOException e) {
            ErrorPrintUtil.printErrorMsg(logger, e);
            return "";
        }
    }

}
