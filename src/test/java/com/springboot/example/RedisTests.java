package com.springboot.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTests {

    private static Logger logger = LoggerFactory.getLogger(RedisTests.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void string() throws InterruptedException {
        redisTemplate.delete("string");

        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set("string", "foo");
        String string = ops.get("string");
        logger.info(">>>>> string: {}", string);      // foo
        String startEnd = ops.get("string",0, 1);
        logger.info(">>>>> startEnd: {}", startEnd);        // fo

        ops.set("string", "oo", 3);
        string = ops.get("string");
        logger.info(">>>>> string: {}", string);      // foooo

        ops.append("string", "oo");
        string = ops.get("string");
        logger.info(">>>>> string: {}", string);      // foooooo

        ops.set("string", "bar", 10, TimeUnit.SECONDS);
        string = ops.get("string");
        logger.info(">>>> string: {}", string);       // bar
        Thread.sleep(10000);
        string = ops.get("string");
        logger.info(">>>> string: {}", string);       // null
    }

    @Test
    public void list() {
        redisTemplate.delete("list");

        ListOperations<String, String> ops = redisTemplate.opsForList();
        ops.leftPush("list", "a");
        ops.leftPush("list", "b");
        ops.leftPush("list", "c");
        ops.rightPush("list", "a");
        ops.rightPush("list", "b");
        ops.rightPush("list", "c");

        List list = ops.range("list", 0, -1);
        logger.info(">>>>> list: {}", list);        // ["c", "b", "a", "a", "b", "c"]

        ops.remove("list", -1, "a");
        list = ops.range("list", 0, -1);
        logger.info(">>>>> list: {}", list);        // ["c", "b", "a", "b", "c"]

        ops.remove("list", 1, "b");
        list = ops.range("list", 0, -1);
        logger.info(">>>>> list: {}", list);        // ["c", "a", "b", "c"]

        ops.remove("list", 0, "c");
        list = ops.range("list", 0, -1);
        logger.info(">>>>> list: {}", list);        // ["a", "b"]
    }

    @Test
    public void set() {
        redisTemplate.delete("set");

        SetOperations<String, String> ops = redisTemplate.opsForSet();
        ops.add("set", "a");
        ops.add("set", "a");
        ops.add("set", "b");
        ops.add("set", "b");
        ops.add("set", "c");
        ops.add("set", "c");
        Long size = ops.size("set");
        logger.info(">>>>> size: {}", size);        // 3
        Set<String> set = ops.members("set");
        logger.info(">>>>> set: {}", set);      // ["a", "b", "c"]，顺序可变

        Boolean  isMember = ops.isMember("set", "a");
        logger.info(">>>>> isMember about a: {}", isMember);        // true
        isMember = ops.isMember("set", "d");
        logger.info(">>>>> isMember about d: {}", isMember);        // false

        ops.remove("set", "b", "c");
        set = ops.members("set");
        logger.info(">>>>> set: {}", set);      // a
    }

    @Test
    public void hash() {
        redisTemplate.delete("hash");

        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        ops.put("hash", "foo", "foo");
        ops.put("hash", "foo", "bar");
        ops.put("hash", "bar", "baz");
        Long size = ops.size("hash");
        logger.info(">>>>> size: {}", size);        // 2
        Set<String> keys = ops.keys("hash");
        logger.info(">>>>> keys: {}", keys);        // ["foo", "bar"]，顺序可变
        List<String> values = ops.values("hash");
        logger.info(">>>>> values: {}", values);        // ["bar", "baz"]
        Map<String, String> map = ops.entries("hash");
        logger.info(">>>>> map: {}", map);      // {"foo": "bar", "bar": "baz"}
        ops.delete("hash", "bar");
        map = ops.entries("hash");
        logger.info(">>>>> map: {}", map);      // {"foo":"bar"}
    }

    @Test
    public void zset() {
        redisTemplate.delete("hash");

        ZSetOperations<String, String> ops = redisTemplate.opsForZSet();
        ops.add("zset", "a", 1);
        ops.add("zset", "b", 2);
        Set<ZSetOperations.TypedTuple<String>> set = new HashSet<>();
        set.add(new DefaultTypedTuple<>("c", 4.0));
        set.add(new DefaultTypedTuple<>("d", 3.0));
        ops.add("zset", set);
        Set<String> zset = ops.range("zset", 0, -1);
        logger.info(">>>>> zset: {}", zset);        // ["a", "b", "d", "c"]

        Set<String> rangeByScore = ops.rangeByScore("zset", 2, 3);
        logger.info(">>>>> rangeByScore: {}", rangeByScore);      // ["b", "d"]
        rangeByScore = ops.rangeByScore("zset", 2, 3, 0, 1);
        logger.info(">>>>> rangeByScore: {}", rangeByScore);      // ["b"]
    }

}
