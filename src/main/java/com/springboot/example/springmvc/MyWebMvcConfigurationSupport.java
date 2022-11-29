package com.springboot.example.springmvc;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyWebMvcConfigurationSupport extends WebMvcConfigurationSupport {

    @Value("${response.message.json.pretty.format:false}")
    private boolean responseMessageJsonPrettyFormat;

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");  // 允许访问 swagger-ui.html
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");  // 允许访问 swagger-ui.html
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 配置 alibaba fastjson 消息转换器
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        if (responseMessageJsonPrettyFormat) {
            fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        }

        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);

        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        fastConverter.setFastJsonConfig(fastJsonConfig);
        fastConverter.setSupportedMediaTypes(mediaTypes);
        converters.add(fastConverter);
    }
}
