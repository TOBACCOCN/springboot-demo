package com.springboot.example.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * OkHttp 工具单元测试
 *
 * @author zhangyonghong
 * @date 2019.6.14
 */
@Slf4j
public class OkHttpUtilTest {

    // private static Logger logger = LoggerFactory.getLogger(OkHttpUtilTests.class);

    @Test
    public void httpGet() throws Exception {
        String url = "https://www.baidu.com";
        String result = OkHttpUtil.httpGet(url);
        log.info(">>>>> RESULT: [{}]", result);
    }

    @Test
    public void httpPost() throws Exception {
        // String url = "http://127.0.0.1:9527/hello";
        String url = "https://127.0.0.1:9527/hello";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", UUID.randomUUID().toString().replaceAll("-", ""));
        String contentType = "application/x-www-form-urlencoded";
        String param = "foo=bar&bar=barz";
        String result = OkHttpUtil.httpPost(url, headerMap, contentType, param);
        log.info(">>>>> RESULT: [{}]", result);
    }

    @Test
    public void httpPostJSON() throws Exception {
        // String url = "http://127.0.0.1:9527/helloJSON";
        String url = "https://127.0.0.1:9527/helloJSON";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", UUID.randomUUID().toString().replaceAll("-", ""));
        String contentType = "application/json";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("foo", "bar");
        paramMap.put("bar", "barz");
        String param = JSON.toJSONString(paramMap);
        String result = OkHttpUtil.httpPost(url, headerMap, contentType, param);
        log.info(">>>>> RESULT: [{}]", result);
    }

}
