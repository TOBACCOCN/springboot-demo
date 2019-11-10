package com.springboot.example.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.example.domain.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 学生 DAO 单元测试
 *
 * @author zhangyonghong
 * @date 2019.9.19
 */
// 工程中开启有 websocket 时，@SpringBootTest 注解需要添加参数 webEnvironment，
// 否者会抛异常：javax.websocket.server.ServerContainer not available
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class StudentMapperTests {

    // student 表不分片

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void insert() {
        Student student = new Student(6L, "zhouba", 23, "suzhou");
        int insertAffect = studentMapper.insert(student);
        log.info(">>>>> INSERT_AFFECT: [{}]", insertAffect);
    }

    @Test
    public void selectOne() {
        Student studentFound = studentMapper.selectOne(new QueryWrapper<Student>().lambda().eq(Student::getAddress, "shanghai")
                .eq(Student::getName, "zhangsan"));
        log.info(">>>>> STUDENT_FOUND: [{}]", studentFound == null ? "null" : studentFound);
    }

    @Test
    public void selectList() {
        List<Student> students = studentMapper.selectList(new QueryWrapper<Student>().lambda().eq(Student::getAddress, "shanghai")
                .or().eq(Student::getName, "zhangsan"));
        log.info(">>>>> STUDENTS: [{}]", students);
    }

    @Test
    public void selectPage() {
        // sharding-jdbc 分页暂时有问题，https://github.com/apache/incubator-shardingsphere/issues/2926
        Page<Student> page = new Page<>(1, 2);
        IPage<Student> studentIPage = studentMapper.selectPage(page, new QueryWrapper<>());
        log.info(">>>>> TOTAL: [{}]", studentIPage.getTotal());
        log.info(">>>>> RECORDS: [{}]", studentIPage.getRecords());
    }

    @Test
    public void update() {
        Student student = new Student();
        student.setAddress("wuhan");
        int updateAffect = studentMapper.update(student, new QueryWrapper<Student>().lambda().eq(Student::getAddress, "suzhou"));
        log.info(">>>>> UPDATE_AFFECT: [{}]", updateAffect);
    }

    @Test
    public void delete() {
        int deleteAffect = studentMapper.delete(new QueryWrapper<Student>().lambda().eq(Student::getAddress, "wuhan"));
        log.info(">>>>> DELETE_AFFECT: [{}]", deleteAffect);
    }

}
