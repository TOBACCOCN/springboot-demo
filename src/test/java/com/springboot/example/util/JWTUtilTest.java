package com.springboot.example.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Slf4j
class JWTUtilTest {

    @Test
    void signHS512() throws NoSuchAlgorithmException, InvalidKeyException {
        JSONObject jsonObj  = new JSONObject();
        jsonObj.put("sub", "linux no 1");
        jsonObj.put("name", "linus");
        String payload = jsonObj.toJSONString();
        String token = JWTUtil.signHS512(payload);
        log.info(">>>>> TOKEN: [{}]", token);
    }

    @Test
    void verifyHS512() throws Exception {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsaW51eCBubyAxIiwibmFtZSI6ImxpbnVzIn0.x53t8QgHLDmKApGVFPuTXMZCgt-6S1kKWm_p-c6xsE0";
        boolean result = JWTUtil.verifyHS512(token);
        log.info(">>>>> VERIFY_RESULT: [{}]",result);
    }

    @Test
    void splitToken() throws Exception {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsaW51eCBubyAxIiwibmFtZSI6ImxpbnVzIn0.x53t8QgHLDmKApGVFPuTXMZCgt-6S1kKWm_p-c6xsE0";
        String[] array = JWTUtil.splitToken(token);
        log.info(">>>>> ARRAY: [{}]", Arrays.asList(array));
    }
}