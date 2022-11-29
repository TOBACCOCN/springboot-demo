package com.springboot.example.common;

import lombok.Getter;

/**
 * 返回结果枚举类
 *
 * @author zhangyonghong
 * @date 2019.12.15
 */
@Getter
public enum ResultEnum {

    SUCCESS("2000", "Success"),
    NO_ACCOUNT_BOUND("2001", "NO_ACCOUNT_BOUND"),
    SHARED_TO_THIS_USER_ALREADY("2002", "Shared to this user already"),
    BOUND_ALREADY("2003", "Bound already"),

    INVALID_PHONE("4000", "Invalid phone"),
    DUPLICATE_PHONE("4001", "Duplicate phone"),
    INVALID_SMS_VERIFICATION_CODE("4002", "Invalid smsVerificationCode"),
    NOT_ALLOW_BLANK_PASSWORD("4003", "Not allow blank password"),
    INVALID_EMAIL("4004", "Invalid email"),
    DUPLICATE_EMAIL("4005", "Duplicate email"),
    // INVALID_ACCOUNT_OR_PASSWORD("4006", "Invalid account or password"),
    ACCOUNT_NOT_EXIST("4006", "Account not exist"),
    EMAIL_NOT_ACTIVATE("4007", "Email not activate"),
    PASSWORD_NOT_CORRECT("4008", "Password not correct"),
    EXPIRED_WECHAT_LOGIN_SESSION("4009", "Expired wechat login session"),
    // INVALID_BOOLEAN_VALUE("4011", "Invalid boolean value"),
    INVALID_UID("4010", "Invalid uid"),
    COULD_NOT_SHARE_TO_SELF("4011", "Could not share to self"),
    INVALID_SHARE_CODE("4012", "Invalid share code"),
    COULD_NOT_REMOVE_FROM_SELF("4013", "Could not remove from self"),
    NO_PERMISSION("4014", "No permission"),
    TOO_FREQUENT_ACCESS("4015", "Too frequent access"),
    CONNECTION_AUTH_FAILED("4016", "Connection auth failed"),
    // BOUND_ALREADY("4017", "Bound already"),
    INVALID_ALARM_TYPE("4017", "Invalid alarmType"),
    INVALID_SIGN("4018", "Invalid sign"),
    INVALID_FILE_QUANTITY("4019", "Invalid file quantity"),
    FILE_TYPE_NOT_SUPPORT("4020", "File type not support"),
    INVALID_TIME("4021", "Invalid time"),
    FILE_NOT_EXIST("4022", "File not exist"),
    INVALID_TYPE("4023", "Invalid type"),
    REGISTER_ALREADY("4024", "Register already"),
    TOKEN_NOT_CORRECT("4025", "Token not correct"),
    OVER_BOUND_USERS_THRESHOLD("4026", "Over bound users threshold"),
    INVALID_CUSTOMER_NAME("4027", "Invalid customer name"),

    FAILED("5000", "Failed");

    private final String code;
    private final String message;

    ResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
