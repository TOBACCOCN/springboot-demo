package com.springboot.example.domain;

import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Date;

@TableName("STUDENT_INFO")
public class StudentInfo {

    private long id;

    private String name;

    private String sex;

    private Date create_date;

}
