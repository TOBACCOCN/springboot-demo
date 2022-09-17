package com.springboot.example.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Date;

@Data
@TableName("STUDENT_INFO")
public class StudentInfo {

    private long id;

    private String name;

    private String sex;

    private Date createDate;

}
