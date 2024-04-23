package com.springboot.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.springboot.example.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 用户接口实现
 *
 * @author zhangyonghong
 * @date 2019.7.16
 */
public interface UserService {

    int create(User user);

    User findById(Long id);

    List<User> findAll();

    IPage<User> findPage(long current, long size);

    Map<String, Object> queryByIdAndName(Long id, String name);

}
