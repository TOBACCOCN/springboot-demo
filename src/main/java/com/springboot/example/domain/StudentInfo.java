package com.springboot.example.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@Data
@TableName("STUDENT_INFO")
public class StudentInfo {

    private Long id;

    private String name;

    private String sex;

    private Date createDate;

}
