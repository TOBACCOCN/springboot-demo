package com.springboot.example.dao.oracle;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.example.datasource.DynamicDataSourceNameEnum;
import com.springboot.example.datasource.MyDataSource;
import com.springboot.example.domain.StudentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * 学生 DAO
 *
 * @author zhangyonghong
 * @date 2019.9.19
 */
@Mapper
// @DS("oracle")
public interface StudentInfoMapper extends BaseMapper<StudentInfo> {

    @MyDataSource(DynamicDataSourceNameEnum.ORACLE)
    // @Select("select id, name, sex, to_char(create_date) create_date from STUDENT_INFO where id = #{id}")
    @Select("select id, name, sex, to_char(create_date, 'yyyy/MM/dd') createDate from STUDENT_INFO where id = #{id}")
    Map<String, Object> queryById(long id);

}
