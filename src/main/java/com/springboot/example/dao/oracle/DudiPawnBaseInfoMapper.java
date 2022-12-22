package com.springboot.example.dao.oracle;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.example.domain.DudiPawnBaseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DudiPawnBaseInfo DAO
 *
 * @author zhangyonghong
 * @date 2019.9.20
 */
@Mapper
// @DS("oracle")
public interface DudiPawnBaseInfoMapper extends BaseMapper<DudiPawnBaseInfo> {

    int batchUpdate(@Param("dudiPawnBaseInfos") List<DudiPawnBaseInfo> dudiPawnBaseInfos);

}
