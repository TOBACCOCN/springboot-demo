package com.springboot.example.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springboot.example.domain.User;
import com.springboot.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;
import java.util.UUID;
import java.util.function.Function;

/**
 * 反射工具测试类
 *
 * @author TOBACCO
 * @date 2020.08.23
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class ReflectUtilTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void invoke() throws Exception {
        String className = UserService.class.getName();

        // Method method = UserService.class.getMethod("findAll");
        Method method = UserService.class.getMethod("create", User.class);
        JSONObject jsonObjOfParamTypeName2ParamBeanJson = new JSONObject();
        jsonObjOfParamTypeName2ParamBeanJson.put(User.class.getName(), JSON.toJSONString(new User(9L, "6", 6, "6")));

        String methodName = method.getName();

        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        String transactionId = UUID.randomUUID().toString().replaceAll("-", "");

        // ops.put("hash", transactionId, ReflectUtil.serialize(className, methodName, null, returnTypeName));
        ops.put("TRANSACTION", transactionId, ReflectUtil.serialize(className, methodName, jsonObjOfParamTypeName2ParamBeanJson));

        String paramTypeName2ParamBeanJson = ops.get("TRANSACTION", transactionId);
        Function<Class<?>, ?> function = SimpleApplicationContextAware.getApplicationContext()::getBean;
        Object result = ReflectUtil.invoke(paramTypeName2ParamBeanJson, function);

        log.info(">>>>> INVOKE_RESULT: [{}]", result);
    }

}
