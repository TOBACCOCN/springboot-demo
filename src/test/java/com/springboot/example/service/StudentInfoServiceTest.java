package com.springboot.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.springboot.example.domain.StudentInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 学生接口单元测试(oracle)
 *
 * @author TOBACCO
 * @date 2022.12.19
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
@RequiredArgsConstructor
public class StudentInfoServiceTest {

    @Resource
    private StudentInfoService studentInfoService;

    @Test
    public void insert() {
        for (int i = 0; i < 4; i++) {
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setId(i + 3);
            studentInfo.setName("zhouba");
            studentInfo.setSex("M");
            studentInfo.setCreateDate(new Date());
            int insertAffect = studentInfoService.create(studentInfo);
            log.info(">>>>> INSERT_AFFECT: [{}]", insertAffect);
        }
    }

    @Test
    public void findById() {
        // DynamicDataSource.setDataSource(DynamicDataSourceNameEnum.ORACLE);
        StudentInfo studentInfoFound = studentInfoService.findById(2L);
        log.info(">>>>> STUDENTINFO_FOUND: [{}]", studentInfoFound == null ? "null" : studentInfoFound);
    }

    @Test
    public void findAll() {
        List<StudentInfo> studentInfos = studentInfoService.findAll();
        log.info(">>>>> STUDENT_INFOS: [{}]", studentInfos);
    }

    @Test
    public void findPage() {
        IPage<StudentInfo> page = studentInfoService.findPage(2, 3);
        log.info(">>>>> TOTAL: [{}]", page.getTotal());
        log.info(">>>>> RECORDS: [{}]", page.getRecords());
    }

}
