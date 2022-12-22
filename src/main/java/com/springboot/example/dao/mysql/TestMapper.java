package com.springboot.example.dao.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.example.domain.Test;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学生 DAO
 *
 * @author zhangyonghong
 * @date 2019.9.19
 */
@Mapper
public interface TestMapper extends BaseMapper<Test> {
}
