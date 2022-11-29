package com.springboot.example.domain;

import com.springboot.example.validation.ByteLength;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.MapKey;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("name")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @ApiModelProperty("age")
    @Digits(integer = 3, fraction = 0, message = "年龄")
    @Min(value = 10, message = "年龄最小 10 岁")
    @Max(value = 128, message = "年龄最大 128 岁")
    private Integer age;

    @ApiModelProperty("address")
    @ByteLength(min = 3, max = 20, message = "地址字节数为 3 到 20")
    private String address;

}
