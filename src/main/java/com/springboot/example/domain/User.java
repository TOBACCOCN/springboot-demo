package com.springboot.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 用户实体类
 *
 * @author zhangyonghong
 * @date 2019.6.10
 */
@Component
@PropertySource("classpath:user.properties")
@ConfigurationProperties(prefix = "user")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements Serializable {

    // user 表要分片，库也要分片

    private Long id;

    private String name;

    private Integer age;

    private String address;

}
