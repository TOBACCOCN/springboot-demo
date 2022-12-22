package com.springboot.example.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DynamicDataSourceConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.mysql")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.oracle")
    public DataSource oracleDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DynamicDataSource dynamicDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DynamicDataSourceNameEnum.MYSQL, mysqlDataSource());
        targetDataSources.put(DynamicDataSourceNameEnum.ORACLE, oracleDataSource());
        DynamicDataSource dynamicDataSource = new DynamicDataSource(targetDataSources, mysqlDataSource());
        return dynamicDataSource;
    }

}
