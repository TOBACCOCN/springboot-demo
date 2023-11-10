package com.springboot.example.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
public class JWTUtil {

    private static final String KEY;
    private static final String ALGORITHM_HS512 = "HmacSHA256";

    static {
        try {
            KEY = MD5Util.encode("JJWT".getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param payload: 数据负载
     * @return java.lang.String jwt token
     */
    public static String signHS512(String payload) throws NoSuchAlgorithmException, InvalidKeyException {
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        payload = encoder.encodeToString(payload.getBytes());

        JSONObject headerJsonObj = new JSONObject();
        headerJsonObj.put("alg", "HS512");
        headerJsonObj.put("typ", "JWT");
        String header = encoder.encodeToString(headerJsonObj.toJSONString().getBytes());

        Mac mac = Mac.getInstance(ALGORITHM_HS512);
        mac.init(new SecretKeySpec(KEY.getBytes(), ALGORITHM_HS512));
        mac.update(header.getBytes());
        mac.update((byte) 46);
        String sign = encoder.encodeToString(mac.doFinal(payload.getBytes()));

        return header + "." + payload + "." + sign;
    }

    public static boolean verifyHS512(String token) throws Exception {
        String[] array = splitToken(token);
        String header = new String(Base64.getUrlDecoder().decode(array[0]));
        log.info(">>>>> HEADER: [{}]", header);
        String payload = new String(Base64.getUrlDecoder().decode(array[1]));
        log.info(">>>>> PAYLOAD: [{}]", payload);

        Mac mac = Mac.getInstance(ALGORITHM_HS512);
        mac.init(new SecretKeySpec(KEY.getBytes(), ALGORITHM_HS512));
        mac.update(array[0].getBytes());
        mac.update((byte) 46);
        return MessageDigest.isEqual(array[2].getBytes(), Base64.getUrlEncoder().withoutPadding().encodeToString(mac.doFinal(array[1].getBytes())).getBytes());
    }

    public static String[] splitToken(String token) throws Exception {
        if (token == null) {
            throw new Exception("The token is null.");
        } else {
            int firstPeriodIndex = token.indexOf(".");
            if (firstPeriodIndex == -1) {
                throw new Exception(String.format("The token was expected to have 3 parts, but got %s.", 1));
            }

            int secondPeriodIndex = token.indexOf(".", firstPeriodIndex + 1);
            if (secondPeriodIndex == -1) {
                throw new Exception(String.format("The token was expected to have 3 parts, but got %s.", 2));
            }

            return new String[]{token.substring(0, firstPeriodIndex), token.substring(firstPeriodIndex + 1, secondPeriodIndex), token.substring(secondPeriodIndex + 1)};
        }
    }

}