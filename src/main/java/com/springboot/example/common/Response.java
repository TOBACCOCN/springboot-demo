package com.springboot.example.common;

import com.alibaba.fastjson.JSONObject;

/**
 * 返回结果类
 *
 * @author zhangyonghong
 * @date 2019.12.15
 */
public class Response extends JSONObject {

    private Response() {
    }

    private <T> Response(boolean success, int status, String msg, T t) {
        put("success", success);
        put("status", status);
        put("msg", msg);
        if (t != null) {
            put("data", t);
        }
    }

    public static Response of(ResponseEnum responseEnum) {
        return of(responseEnum, null);
    }

    public static <T> Response of(ResponseEnum responseEnum, T t) {
        return of(responseEnum.isSuccess(), responseEnum.getStatus(), responseEnum.getMsg(), t);
    }

    public static <T> Response of(boolean success, int status, String msg) {
        return  of(success, status, msg, null);
    }

    public static <T> Response of(boolean success, int status, String msg, T t) {
        return  new Response(success, status, msg, t);
    }
}
