package com.springboot.example.dao.mysql;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.springboot.example.dao.mysql.UserMapper;
import com.springboot.example.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户 DAO 单元测试
 *
 * @author zhangyonghong
 * @date 2019.6.3
 */
// 工程中开启有 websocket 时，@SpringBootTest 注解需要添加参数 webEnvironment，
// 否者会抛异常：javax.websocket.server.ServerContainer not available
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class UserMapperTest {

    // user 表要分片，库也要分片

    // private static Logger logger = LoggerFactory.getLogger(UserMapperTests.class);

    @Autowired
    private UserMapper userMapper;

    @Test
    public void queryById() {
        Map<String, Object> map = userMapper.queryById(1L);
        log.info(">>>>> MAP: [{}]", map);
    }

    @Test
    public void queryByIdAndName() {
        Map<String, Object> map = userMapper.queryByIdAndName(1L, "2");
        log.info(">>>>> map: [{}]", map);
    }

    @Test
    public void queryByIds() {
        List<Map<String, Object>> maps = userMapper.queryByIds(Lists.newArrayList(1L, 2L));
        log.info(">>>>> MAPS: [{}]", maps);
    }

    @Test
    public void queryByMap() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "2");
        map.put("address", "2");
        Map<String, Object> result = userMapper.queryByMap(map);
        log.info(">>>>> RESULT: [{}]", result);
    }

    @Test
    public void insert() {
        // User user = new User(6L, "zhouba", 23, "suzhou");
        // int insertAffect = userMapper.insert(user);
        // log.info(">>>>> INSERT_AFFECT: [{}]", insertAffect);
        int affect = 0;
        for (int i = 0; i < 10000; i++) {
            affect += userMapper.insert(new User((long) i, i + "", i, i + ""));
        }
        // Assert.isTrue(affect == 10000, "userMapper.insert failed");
        Assert.assertEquals("userMapper.insert failed", 10000, affect);
    }

    @Test
    public void selectOne() {
        User userFound = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getAddress, "shanghai")
                .eq(User::getName, "zhangsan"));
        log.info(">>>>> USER_FOUND: [{}]", userFound == null ? "null" : userFound);
    }

    @Test
    public void selectList() {
        List<User> users = userMapper.selectList(new QueryWrapper<User>().lambda().eq(User::getAddress, "shanghai")
                .or().eq(User::getName, "zhangsan"));
        log.info(">>>>> USERS: [{}]", users);
    }

    @Test
    public void selectConditionList() {
        List<User> users = userMapper.selectList(new LambdaQueryWrapper<User>().eq(User::getAddress, "2")
                .and(wrapper -> wrapper.gt(User::getAge, 3))
                .or(wrapper -> wrapper.eq(User::getName, "5").or().eq(User::getName, "6")));
        // SELECT id,name,age,address FROM user WHERE (address = '2' AND (age > 3) OR (name = '5' OR name = '6'))

        // List<User> users = userMapper.selectList(new LambdaQueryWrapper<User>().eq(User::getAddress, "2")
        //         .or().gt(User::getAge, 3).eq(User::getAddress, "5"));
        // SELECT id,name,age,address FROM user WHERE (address = '2' OR age > 3 AND address = '5')
        log.info(">>>>> USERS: [{}]", users);
    }

    @Test
    public void selectPage() {
        // sharding-jdbc 分页暂时有问题，https://github.com/apache/incubator-shardingsphere/issues/2926
        Page<User> page = new Page<>(1, 2);
        IPage<User> userIPage = userMapper.selectPage(page, new QueryWrapper<>());
        log.info(">>>>> TOTAL: [{}]", userIPage.getTotal());
        log.info(">>>>> RECORDS: [{}]", userIPage.getRecords());
    }

    @Test
    public void update() {
        User user = new User();
        user.setAddress("wuhan");
        int updateAffect = userMapper.update(user, new QueryWrapper<User>().lambda().eq(User::getAddress, "suzhou"));
        log.info(">>>>> UPDATE_AFFECT: [{}]", updateAffect);
    }

    @Test
    public void delete() {
        int deleteAffect = userMapper.delete(new QueryWrapper<User>().lambda().eq(User::getAddress, "wuhan"));
        log.info(">>>>> DELETE_AFFECT: [{}]", deleteAffect);
    }

    @Test
    public void batchUpdate() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            users.add(new User((long) i, (i + 1) + "", i + 1, (i + 1) + ""));
        }
        Lists.partition(users, 1000).forEach(list -> {
            int affect = userMapper.batchUpdate(list);
            // Assert.isTrue(affect == 100, "userMapper.batchUpdate failed");
            Assert.assertEquals("userMapper.batchUpdate failed", 100, affect);
        });
    }

}
