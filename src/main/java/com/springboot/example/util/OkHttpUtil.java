package com.springboot.example.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.util.Map;

/**
 * OkHttp 工具类
 *
 * @author zhangyonghong
 * @date 2019.6.14
 */
@Slf4j
public class OkHttpUtil {

    // private static Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);

    private OkHttpUtil() {
    }

    /**
     * GET 请求
     *
     * @param url 请求地址
     * @return 响应消息
     */
    public static String httpGet(String url) throws Exception {
        OkHttpClient okHttpClient = getOkHttpClient(url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        return getResponse(okHttpClient, request);
        // 2.发送异步请求，适合在 android 上使用
        // SimpleCallback simpleCallback = new SimpleCallback();
        // call.enqueue(simpleCallback);
        // return "";
    }

    private static OkHttpClient getOkHttpClient(String url) throws Exception {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (url.startsWith("https")) {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            SimpleX509TrustManager simpleX509TrustManager = new SimpleX509TrustManager();
            TrustManager[] trustManager = {simpleX509TrustManager};
            sslContext.init(null, trustManager, null);
            builder.hostnameVerifier((s, sslSession) -> true)
                    .sslSocketFactory(sslContext.getSocketFactory(), simpleX509TrustManager);
        }
        return builder.build();
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
    public static String httpPost(String url, Map<String, String> headerMap, String contentType, String param) throws Exception {
        OkHttpClient okHttpClient = getOkHttpClient(url);
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
            ErrorPrintUtil.printErrorMsg(log, e);
            return "";
        }
    }

}
