package com.springboot.example.rocketmq;

import lombok.Data;

import java.lang.reflect.Method;

@Data
public class TransactionArg<T> {

    private Callback<T> callback;

    private Method method;

}
