package com.springboot.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.springboot.example.domain.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    List<User> findAll();

    IPage<User> find(long current, long size);

}
