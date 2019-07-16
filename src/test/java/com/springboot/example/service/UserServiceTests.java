package com.springboot.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTests {

    private static Logger logger = LoggerFactory.getLogger(UserServiceTests.class);

    @Autowired
    private UserService userService;

    @Test
    public void findById() {
        User userFound = userService.findById(2L);
        logger.info(">>>>> USER_FOUND: {}", userFound == null ? "null" : userFound);
    }

    @Test
    public void findAll() {
        List<User> users = userService.findAll();
        logger.info(">>>>> USERS: {}", users);
    }

    @Test
    public void find() {
        IPage<User> page = userService.find(2, 3);
        logger.info(">>>>> TOTAL: {}", page.getTotal());
        logger.info(">>>>> RECORDS: {}", page.getRecords());
    }

}
