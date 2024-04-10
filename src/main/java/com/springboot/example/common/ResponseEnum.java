package com.springboot.example.common;

import lombok.Getter;

/**
 * 返回结果枚举类
 *
 * @author zhangyonghong
 * @date 2019.12.15
 */
@Getter
public enum ResponseEnum {

    SUCCESS(true, 2000, "Success"),
    INVALID(false, 4000, "Invalid"),
    ERROR(false, 5000, "Error");

    private final boolean success;
    private final int status;
    private final String msg;

    ResponseEnum(boolean success, int status, String msg) {
        this.success = success;
        this.status = status;
        this.msg = msg;
    }

}
