package com.springboot.example.beetl;

import com.ibeetl.starter.BeetlTemplateConfig;
import lombok.Data;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * beetl 模板引擎配置类
 *
 * @author zhangyonghong
 * @date 2019.9.19
 */
@Configuration
@ConfigurationProperties(prefix = "beetl")
@Data
public class BeetlConfiguration {

    private String templatesPath;
    private String suffix;
    private boolean dev;

    @Bean
    public BeetlSpringViewResolver beetlSpringViewResolver() {
        BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
        beetlSpringViewResolver.setSuffix(suffix);

        BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();

        Properties extProperties = new Properties();
        if (dev) {
            extProperties.put("RESOURCE.autoCheck", "true");
        } else {
            extProperties.put("RESOURCE.autoCheck", "false");
        }
        beetlGroupUtilConfiguration.setConfigProperties(extProperties);

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = BeetlTemplateConfig.class.getClassLoader();
        }

        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader(loader, templatesPath);
        beetlGroupUtilConfiguration.setResourceLoader(resourceLoader);
        beetlGroupUtilConfiguration.init();
        beetlGroupUtilConfiguration.getGroupTemplate().setClassLoader(loader);

        beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
        return beetlSpringViewResolver;
    }
}
