package com.springboot.example.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClient 工具类
 *
 * @author zhangyonghong
 * @date 2019.6.13
 */
public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * GET 请求
     *
     * @param url 请求地址
     * @return 响应消息
     */
    public static String httpGet(String url) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        logger.info(">>>>> SENDING HTTP_GET");
        HttpResponse response = httpClient.execute(httpGet);

        String result = "";
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            logger.info(">>>>> RESPONSE SUCCESS");
            result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        }
        return result;
    }

    /**
     * POST 请求
     *
     * @param url       请求地址
     * @param headerMap 请求头参数
     * @param paramMap  请求参数
     * @return 响应消息
     */
    public static String httpPost(String url, Map<String, String> headerMap, Map<String, String> paramMap) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        headerMap.forEach(httpPost::addHeader);
        List<NameValuePair> nameValuePairList = new ArrayList<>();
        paramMap.forEach((key, value) -> nameValuePairList.add(new BasicNameValuePair(key, value)));
        HttpEntity entity = new UrlEncodedFormEntity(nameValuePairList, StandardCharsets.UTF_8);
        return getResponse(httpClient, httpPost, entity);
    }

    /**
     * 设置请求体信息并执行请求
     *
     * @param httpClient 请求客户端
     * @param httpPost   请求对象
     * @param entity     请求体
     * @return 响应信息
     */
    private static String getResponse(HttpClient httpClient, HttpPost httpPost, HttpEntity entity) throws IOException {
        httpPost.setEntity(entity);
        String result = "";
        logger.info(">>>>> SENDING HTTP_POST");
        HttpResponse response = httpClient.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            logger.info(">>>>> RESPONSE SUCCESS");
            result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        }
        return result;
    }

    /**
     * POST 请求
     *
     * @param url       请求地址
     * @param headerMap 请求头参数
     * @param json      请求参数
     * @return 响应消息
     */
    public static String httpPostJSON(String url, Map<String, String> headerMap, String json) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        headerMap.forEach(httpPost::addHeader);
        HttpEntity entity = new StringEntity(json, StandardCharsets.UTF_8);
        return getResponse(httpClient, httpPost, entity);
    }

}
