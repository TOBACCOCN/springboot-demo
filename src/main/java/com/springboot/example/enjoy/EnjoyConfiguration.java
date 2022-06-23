package com.springboot.example.enjoy;

import com.jfinal.template.ext.spring.JFinalViewResolver;
import com.jfinal.template.source.ClassPathSourceFactory;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * enjoy 模板引擎配置类
 *
 * @author zhangyonghong
 * @date 2019.9.19
 */
@ConditionalOnProperty(prefix = "enjoy", name = "enable", havingValue = "true")
@Configuration
@ConfigurationProperties(prefix = "enjoy")
@Data
public class EnjoyConfiguration {

    private String baseTemplatePath;
    private String suffix;
    private String contentType;

    @Bean
    public JFinalViewResolver jFinalViewResolver() {
        JFinalViewResolver jFinalViewResolver = new JFinalViewResolver();
        // setDevMode 配置放在最前面
        // jFinalViewResolver.setDevMode(true);
        // 使用 ClassPathSourceFactory 从 classpath 与 jar 包中加载模板文件
        jFinalViewResolver.setSourceFactory(new ClassPathSourceFactory());
        // 在使用 ClassPathSourceFactory 时要使用 setBaseTemplatePath 代替 setPrefix("/view/")
        JFinalViewResolver.engine.setBaseTemplatePath(baseTemplatePath);
        jFinalViewResolver.setSuffix(suffix);
        jFinalViewResolver.setContentType(contentType);
        return jFinalViewResolver;
    }

}
