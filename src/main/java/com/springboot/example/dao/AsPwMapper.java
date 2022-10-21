package com.springboot.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.example.domain.User;
import com.springboot.example.dto.AsPwFundraiserProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户 DAO
 *
 * @author zhangyonghong
 * @date 2019.9.20
 */
@Component
@Mapper
public interface AsPwMapper {

    List<AsPwFundraiserProduct> select(AsPwFundraiserProduct pwFundraiserProduct);

}
