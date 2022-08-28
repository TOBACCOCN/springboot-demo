package com.springboot.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.example.domain.Student;
import com.springboot.example.domain.StudentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 学生 DAO
 *
 * @author zhangyonghong
 * @date 2019.9.19
 */
@Component
@Mapper
public interface StudentInfoMapper extends BaseMapper<StudentInfo> {

    // @Select("select id, name, sex, to_char(create_date) create_date from STUDENT_INFO where id = #{id}")
    @Select("select id, name, sex, to_char(create_date, 'yyyy/MM/dd') createDate from STUDENT_INFO where id = #{id}")
    Map<String, Object> selectById(long id);

}
