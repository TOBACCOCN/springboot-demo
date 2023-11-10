package com.springboot.example.datasource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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

    @Value("${spring.datasource.mysql.enable:true}")
    private Boolean mysqlEnabled;
    @Value("${spring.datasource.oracle.enable:false}")
    private Boolean oracleEnabled;

    @Bean
    @ConditionalOnProperty(prefix = "spring.datasource.mysql", name = "enable", havingValue = "true", matchIfMissing = true)
    @ConfigurationProperties("spring.datasource.mysql")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.datasource.oracle", name = "enable", havingValue = "true")
    @ConfigurationProperties("spring.datasource.oracle")
    public DataSource oracleDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DynamicDataSource dynamicDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        if ( mysqlEnabled) {
            targetDataSources.put(DynamicDataSourceNameEnum.MYSQL, mysqlDataSource());
        }
        if (oracleEnabled) {
            targetDataSources.put(DynamicDataSourceNameEnum.ORACLE, oracleDataSource());
        }
        return new DynamicDataSource(targetDataSources, mysqlDataSource());
    }

}
