package com.springboot.example.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpClientUtilTests {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtilTests.class);

    @Test
    public void httpGet() throws IOException {
        String url = "https://www.baidu.com";
        String result = HttpClientUtil.httpGet(url);
        logger.info(">>>>> RESULT: {}", result);
    }

    @Test
    public void httpPost() throws IOException {
        String url = "http://127.0.0.1:9527/hello";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", ContentType.APPLICATION_FORM_URLENCODED.toString());
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("foo", "bar");
        paramMap.put("bar", "barz");
        String result = HttpClientUtil.httpPost(url, headerMap, paramMap);
        logger.info(">>>>> RESULT: {}", result);
    }

    @Test
    public void httpPostJSON() throws IOException {
        String url = "http://127.0.0.1:9527/helloJSON";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", ContentType.APPLICATION_JSON.toString());
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("foo", "bar");
        paramMap.put("bar", "barz");
        String json = JSON.toJSONString(paramMap);
        String result = HttpClientUtil.httpPostJSON(url, headerMap, json);
        logger.info(">>>>> RESULT: {}", result);
    }

}
