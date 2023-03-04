package com.springboot.example.service;

import com.github.jsonzou.jmockdata.JMockData;
import com.springboot.example.dao.oracle.StudentInfoMapper;
import com.springboot.example.domain.StudentInfo;
import com.springboot.example.util.AESUtil;
import com.springboot.example.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 学生接口单元测试(oracle)
 *
 * @author TOBACCO
 * @date 2022.12.19
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
public class StudentInfoServiceMockTest {

    @InjectMocks
    private StudentInfoServiceImpl studentInfoService;
    @Mock
    private StudentInfoMapper studentInfoMapper;

    // @BeforeEach
    // public void init() {
    //     MockitoAnnotations.initMocks(this);
    // }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    public void findById(int flag)  {
        if (flag == 1) {
            MockedStatic<AESUtil> mockedStatic = Mockito.mockStatic(AESUtil.class);
            mockedStatic.when(() -> {
                AESUtil.encrypt(Mockito.any());
            }).thenReturn(JMockData.mock(String.class));
        } else {
            MockedStatic<MD5Util> mockedStatic = Mockito.mockStatic(MD5Util.class);
            mockedStatic.when(() -> {
                MD5Util.encode(Mockito.any());
            }).thenReturn(JMockData.mock(String.class));
        }
        Mockito.when(studentInfoMapper.selectById(Mockito.any())).thenReturn(JMockData.mock(StudentInfo.class));
        StudentInfo studentInfoFound = studentInfoService.findById((long) flag);
        log.info(">>>>> STUDENTINFO_FOUND: [{}]", studentInfoFound == null ? "null" : studentInfoFound);
    }

}
