package com.springboot.example.datasource;

import java.lang.annotation.*;

/**
 * 有事务注解时不支持根据 MyDataSource 切换数据源，可以通过在方法上加 @Transactional(propagation = Propagation.NOT_SUPPORTED) 解决
 *
 * @author TOBACCO
 * @date 2022.12.22
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyDataSource {

    DynamicDataSourceNameEnum value();

}
