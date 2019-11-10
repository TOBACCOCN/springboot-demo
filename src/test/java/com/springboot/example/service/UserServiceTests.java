package com.springboot.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.springboot.example.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 用户接口单元测试
 *
 * @author zhangyonghong
 * @date 2019.7.16
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class UserServiceTests {

    // user 表要分片，库也要分片

    // private static Logger logger = LoggerFactory.getLogger(UserServiceTests.class);

    @Autowired
    private UserService userService;

    @Test
    public void insert() {
        for (int i = 0; i < 4; i++) {
            User user = new User();
            user.setName("zhouba");
            user.setAge(i / 2 + 23);
            user.setAddress("suzhou");
            int insertAffect = userService.create(user);
            log.info(">>>>> INSERT_AFFECT: [{}]", insertAffect);
        }
    }

    @Test
    public void findById() {
        User userFound = userService.findById(2L);
        log.info(">>>>> USER_FOUND: [{}]", userFound == null ? "null" : userFound);
    }

    @Test
    public void findAll() {
        List<User> users = userService.findAll();
        log.info(">>>>> USERS: [{}]", users);
    }

    @Test
    public void findPage() {
        // sharding-jdbc 分页暂时有问题，https://github.com/apache/incubator-shardingsphere/issues/2926
        IPage<User> page = userService.findPage(2, 3);
        log.info(">>>>> TOTAL: [{}]", page.getTotal());
        log.info(">>>>> RECORDS: [{}]", page.getRecords());
    }

}
