package com.springboot.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.example.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper extends BaseMapper<User> {
}
