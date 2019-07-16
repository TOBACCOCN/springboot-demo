package com.springboot.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.example.dao.UserMapper;
import com.springboot.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User findById(Long id) {
        return userMapper.selectById(id);
    }

    public List<User> findAll() {
        return userMapper.selectList(new QueryWrapper<>());
    }

    public IPage<User> find(long current, long size) {
        Page<User> page = new Page<>(current, size);
        return userMapper.selectPage(page, new QueryWrapper<>());
    }

}
