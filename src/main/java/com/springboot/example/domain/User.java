package com.springboot.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@PropertySource("classpath:user.properties")
@ConfigurationProperties(prefix = "user")
public class User implements Serializable {

    public Long id;

    public String name;

    public Integer age;

    public String address;

}
