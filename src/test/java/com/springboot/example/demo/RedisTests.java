package com.springboot.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTests {

    private static Logger logger = LoggerFactory.getLogger(RedisTests.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testString() {
        stringRedisTemplate.opsForValue().set("foo", "bar");
        Assert.assertEquals("bar", stringRedisTemplate.opsForValue().get("foo"));
    }

    @Test
    public void testObject() {
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        operations.set("user", new User(127L, "zyh", 32, "ez"));
        Object user = operations.get("user");
        logger.info(">>>>> user: {}", user);
    }

}
