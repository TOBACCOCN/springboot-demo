package com.springboot.example.dao.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.example.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 用户 DAO
 *
 * @author zhangyonghong
 * @date 2019.9.20
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    int batchUpdate(@Param("users") List<User> users);

    // 单个基础类型及包装类、String 参数，sql 中参数名随便写，但是建议与入参参数名保值一致
    @Select("select * from user where id = #{id}")
    // @Select("select * from user where id = #{ids}")
    Map<String, Object> queryById(Long id);

    // 多个入参，sql 中根据 param1, param2 取对应参数的值, 或者直接用入参参数名取值(一定的版本后才支持，如果是低版本的 mybatis或mybatis-plus,可以加注解 @Param 指定 sql 中的参数名)
    // @Select("select * from user where id = #{param1} and name = #{param2}")
    // @Select("select * from user where id = #{id} and name = #{name}")
    Map<String, Object> queryByIdAndName(Long id, String name);
    // Map<String, Object> queryByIdAndName(@Param("id") Long id, @Param("name") String name);

    // list、set 等参数在 sql 中默认可以用 collection 取到值，特别地，List 可以用 list 参数名取到值，数组可以用 array 参数名取到值
    // ParamNameResolver.wrapToMapIfCollection()
    @Select("<script>select * from user where id in <foreach collection='collection' item='id' separator=',' open='(' close=')'>#{id}</foreach></script>")
    List<Map<String, Object>> queryByIds(List<Long> ids);

    // 入参为 map, sql 中根据 map 中 key 的名字取对应参数的值
    @Select("select * from user where name = #{name} and address = #{address}")
    Map<String, Object> queryByMap(Map<String, String> map);

}
