package com.springboot.example.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.example.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
public class UserMapperTests {

    // user 表要分片，库也要分片

    // private static Logger logger = LoggerFactory.getLogger(UserMapperTests.class);

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        User user = new User(6L, "zhouba", 23, "suzhou");
        int insertAffect = userMapper.insert(user);
        log.info(">>>>> INSERT_AFFECT: [{}]", insertAffect);
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

}
