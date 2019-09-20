package com.springboot.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.springboot.example.domain.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 学生接口单元测试
 *
 * @author zhangyonghong
 * @date 2019.9.19
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class StudentServiceTests {

    // student 表不分片

    @Autowired
    private StudentService studentService;

    @Test
    public void insert() {
        for (int i = 0; i < 4; i++) {
            Student student = new Student();
            student.setName("zhouba");
            student.setAge(i / 2 + 23);
            student.setAddress("suzhou");
            int insertAffect = studentService.create(student);
            log.info(">>>>> INSERT_AFFECT: {}", insertAffect);
        }
    }

    @Test
    public void findById() {
        Student studentFound = studentService.findById(2L);
        log.info(">>>>> STUDENT_FOUND: {}", studentFound == null ? "null" : studentFound);
    }

    @Test
    public void findAll() {
        List<Student> students = studentService.findAll();
        log.info(">>>>> STUDENTS: {}", students);
    }

    @Test
    public void findPage() {
        // sharding-jdbc 分页暂时有问题，https://github.com/apache/incubator-shardingsphere/issues/2926
        IPage<Student> page = studentService.findPage(2, 3);
        log.info(">>>>> TOTAL: {}", page.getTotal());
        log.info(">>>>> RECORDS: {}", page.getRecords());
    }

}
