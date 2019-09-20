package com.springboot.example.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * HttpClient 工具单元测试
 *
 * @author zhangyonghong
 * @date 2019.6.14
 */
@Slf4j
public class HttpClientUtilTests {

    // private static Logger logger = LoggerFactory.getLogger(HttpClientUtilTests.class);

    @Test
    public void httpGet() throws Exception {
        String url = "https://www.baidu.com";
        String result = HttpClientUtil.httpGet(url);
        log.info(">>>>> RESULT: {}", result);
    }

    @Test
    public void httpPost() throws Exception {
        // String url = "http://127.0.0.1:9527/hello";
        String url = "https://127.0.0.1:9527/hello";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", ContentType.APPLICATION_FORM_URLENCODED.toString());
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("foo", "bar");
        paramMap.put("bar", "barz");
        String result = HttpClientUtil.httpPost(url, headerMap, paramMap);
        log.info(">>>>> RESULT: {}", result);
    }

    @Test
    public void httpPostJSON() throws Exception {
        // String url = "http://127.0.0.1:9527/helloJSON";
        String url = "https://127.0.0.1:9527/helloJSON";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", ContentType.APPLICATION_JSON.toString());
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("foo", "bar");
        paramMap.put("bar", "barz");
        String json = JSON.toJSONString(paramMap);
        String result = HttpClientUtil.httpPostJSON(url, headerMap, json);
        log.info(">>>>> RESULT: {}", result);
    }

}
