package com.springboot.example.rocketmq;

import java.lang.reflect.Method;

public interface Callback<T> {

    T doTransaction(Method method);

}
