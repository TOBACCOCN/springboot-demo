package com.springboot.example.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.springboot.example.domain.DudiPawnBaseInfo;
import com.springboot.example.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户 DAO 单元测试
 *
 * @author zhangyonghong
 * @date 2019.6.3
 */
// 工程中开启有 websocket 时，@SpringBootTest 注解需要添加参数 webEnvironment，
// 否者会抛异常：javax.websocket.server.ServerContainer not available
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class DudiPawnBaseInfoMapperTest {

    @Autowired
    private DudiPawnBaseInfoMapper dudiPawnBaseInfoMapper;

    @Test
    public void select() {
        List<DudiPawnBaseInfo> dudiPawnBaseInfos = dudiPawnBaseInfoMapper.selectList(new QueryWrapper<>());
        log.info(">>>>> dudiPawnBaseInfos: [{}]", dudiPawnBaseInfos);
    }

    @Test
    public void insert() {
        for (int i = 0; i < 10000; i++) {
            dudiPawnBaseInfoMapper.insert(new DudiPawnBaseInfo( i + "", i + "", i + "", i + ""));
        }
    }

    @Test
    public void batchUpdate() {
        List<DudiPawnBaseInfo> dudiPawnBaseInfos = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            dudiPawnBaseInfos.add(new DudiPawnBaseInfo(i + "", (i + 1000000) + "", (i + 1000000) + "", (i + 1000000) + ""));
        }
        Lists.partition(dudiPawnBaseInfos, 1000).forEach(list -> dudiPawnBaseInfoMapper.batchUpdate(list));
    }

}
