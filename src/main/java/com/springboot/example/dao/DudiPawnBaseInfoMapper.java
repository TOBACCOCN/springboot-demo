package com.springboot.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.example.domain.DudiPawnBaseInfo;
import com.springboot.example.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DudiPawnBaseInfo DAO
 *
 * @author zhangyonghong
 * @date 2019.9.20
 */
@Component
@Mapper
public interface DudiPawnBaseInfoMapper extends BaseMapper<DudiPawnBaseInfo> {

    int batchUpdate(@Param("dudiPawnBaseInfos") List<DudiPawnBaseInfo> dudiPawnBaseInfos);

}
