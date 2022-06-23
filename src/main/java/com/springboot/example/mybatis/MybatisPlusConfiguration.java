package com.springboot.example.mybatis;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus 配置类
 *
 * @author zhangyonghong
 * @date 2019.6.3
 */
@ConditionalOnProperty(prefix = "mybatis-plus", name = "enable", havingValue = "true")
@Configuration
@MapperScan("com.springboot.example.dao.**.mapper")
public class MybatisPlusConfiguration {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
