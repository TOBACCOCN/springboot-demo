package com.springboot.example;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.example.dao.UserMapper;
import com.springboot.example.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests {

    private static Logger logger = LoggerFactory.getLogger(UserMapperTests.class);

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        User user = new User(6L, "zhouba", 23, "suzhou");
        int insertAffect = userMapper.insert(user);
        logger.info(">>>>> INSERT_AFFECT: {}", insertAffect);
    }

    @Test
    public void selectOne() {
        User userFound = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getAddress, "shanghai")
                .eq(User::getName, "zhangshan"));
        logger.info(">>>>> USER_FOUND: {}", userFound == null ? "null" : userFound);
    }

    @Test
    public void selectList() {
        List<User> users= userMapper.selectList(new QueryWrapper<User>().lambda().eq(User::getAddress, "shanghai")
                .or().eq(User::getName, "zhangshan"));
        logger.info(">>>>> USERS: {}", users);
    }

    @Test
    public void selectPage() {
        Page<User> page = new Page<>(1, 2);
        IPage<User> userIPage = userMapper.selectPage(page, new QueryWrapper<>());
        logger.info(">>>>> TOTAL: {}", userIPage.getTotal());
        logger.info(">>>>> RECORDS: {}", userIPage.getRecords());
    }

    @Test
    public void update() {
        User user = new User();
        user.setAddress("wuhan");
        int updateAffect = userMapper.update(user, new QueryWrapper<User>().lambda().eq(User::getAddress, "suzhou"));
        logger.info(">>>>> UPDATE_AFFECT: {}", updateAffect);
    }

    @Test
    public void delete() {
        int deleteAffect = userMapper.delete(new QueryWrapper<User>().lambda().eq(User::getAddress, "wuhan"));
        logger.info(">>>>> UPDATE_AFFECT: {}", deleteAffect);
    }

}
