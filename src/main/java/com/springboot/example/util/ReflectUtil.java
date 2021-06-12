package com.springboot.example.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class ReflectUtil {

    public static final String CLASS_NAME = "className";
    public static final String METHOD_NAME = "methodName";
    public static final String PARAM_TYPE_NAME_2_PARAM_BEAN_JSON = "paramTypeName2ParamBeanJson";

    public static String serialize(String className, String methodName,
                                   JSONObject jsonObjOfParamTypeName2ParamBeanJson) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CLASS_NAME, className);
        jsonObject.put(METHOD_NAME, methodName);
        jsonObject.put(PARAM_TYPE_NAME_2_PARAM_BEAN_JSON, jsonObjOfParamTypeName2ParamBeanJson);
        return jsonObject.toString();
    }

    @SuppressWarnings({"unchecked"})
    public static Object invoke(String value, Function<Class<?>, ?> function) throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // {"paramTypeName2ParamBeanJson":{"com.springboot.example.domain.User":"{\"address\":\"6\",\"age\":6,\"id\":9,\"name\":\"6\"}"},"methodName":"create","className":"com.springboot.example.service.UserService"}
        JSONObject jsonObject = JSONObject.parseObject(value);
        String className = jsonObject.getString(CLASS_NAME);
        String methodName = jsonObject.getString(METHOD_NAME);
        JSONObject jsonObjOfParamTypeName2ParamBeanJson = jsonObject.getJSONObject(PARAM_TYPE_NAME_2_PARAM_BEAN_JSON);
        Class<?> clazz = Class.forName(className);
        Method method;
        if (jsonObjOfParamTypeName2ParamBeanJson == null) {
            method = clazz.getMethod(methodName);
            return method.invoke(function.apply(clazz));
        }

        Map<String, String> paramTypeName2ParamBeanJsonMap = (Map<String, String>) JSON.parseObject(JSON
                .toJSONString(jsonObjOfParamTypeName2ParamBeanJson), Map.class);

        List<Class<?>> paramTypeList = new ArrayList<>();
        List<Object> paramList = new ArrayList<>();
        for (Map.Entry<String, String> entry : paramTypeName2ParamBeanJsonMap.entrySet()) {
            Class<?> clas = Class.forName(entry.getKey());
            paramTypeList.add(clas);
            paramList.add(JSON.parseObject(entry.getValue(), clas));
        }

        Class<?>[] classes = new Class[paramTypeList.size()];
        paramTypeList.toArray(classes);
        Object[] objects = new Object[paramList.size()];
        paramList.toArray(objects);

        method = clazz.getMethod(methodName, classes);
        return method.invoke(function.apply(clazz), objects);
    }

}
