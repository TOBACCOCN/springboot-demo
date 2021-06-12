package com.springboot.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 学生实体类
 *
 * @author zhangyonghong
 * @date 2019.9.19
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Test implements Serializable {

    private Long id;

    private String name;

}
