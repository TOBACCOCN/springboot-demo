package com.springboot.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.example.dao.mysql.UserMapper;
import com.springboot.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    // user 表要分片，库也要分片

    @Autowired
    private UserMapper userMapper;

    @CacheEvict(value="user", allEntries=true)
    public int create(User user) {
        return userMapper.insert(user);
    }

    // @Cacheable(value = "user", key = "#p0")
    @Cacheable(value = "user", key = "#id")
    public User findById(Long id) {
        return userMapper.selectById(id);
    }

    @Cacheable(value = "user", key = "#root.methodName")
    public List<User> findAll() {
        return userMapper.selectList(new QueryWrapper<>());
    }

    @Cacheable(value = "user", key = "#root.methodName")
    public IPage<User> findPage(long current, long size) {
        // sharding-jdbc 分页暂时有问题，https://github.com/apache/incubator-shardingsphere/issues/2926
        Page<User> page = new Page<>(current, size);
        // current 表示当前页
        return userMapper.selectPage(page, new QueryWrapper<>());
    }

    @Override
    public Map<String, Object> queryByIdAndName(Long id, String name) {
        return userMapper.queryByIdAndName(id, name);
    }

}
