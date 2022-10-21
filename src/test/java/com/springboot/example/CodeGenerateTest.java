package com.springboot.example;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 生成代码
 *
 * @author TOBACCO
 * @date 2022.10.7
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class CodeGenerateTest {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Test
    public void generate() {
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("") // 设置作者
                            .disableOpenDir()   // 执行完不打开输出目录
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:/temp"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.springboot.example") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .entity("entity")
                            .mapper("mapper")
                            .xml("mapper")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            /*.pathInfo(Collections.singletonMap(OutputFile.xml, "mapper"))*/; // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    // builder.addInclude("user", "apps") // 设置需要生成的表名
                    //         .addTablePrefix("test_"); // 设置过滤表前缀
                })
                // .templateEngine(new FreemarkerTemplateEngine()) // 使用 Freemarker 引擎模板，默认的是 Velocity 引擎模板
                .execute();

    }

}
