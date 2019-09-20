package com.springboot.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.example.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 用户 DAO
 *
 * @author zhangyonghong
 * @date 2019.9.20
 */
@Component
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
