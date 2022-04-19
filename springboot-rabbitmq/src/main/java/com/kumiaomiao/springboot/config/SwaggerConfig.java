package com.kumiaomiao.springboot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @author ：m
 * @date ：Created in 2022/4/18 14:58
 */

@Configuration
@EnableWebMvc
public class SwaggerConfig {
    /**
     * 配置Swagger具体参数
     * @return
     */
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.OAS_30)
                .groupName("webApi")
                .apiInfo(apiInfo())
                .select()
                .build();
    }

    /**
     * 自定义API文档信息
     * @return
     */
    private ApiInfo apiInfo(){
        // 作者信息
        Contact contact = new Contact("土味儿", "http://localhost:8080/", "2141421414@qq.com");

        return new ApiInfo(
                "Hello Swagger API 文档",
                "大道无垠 行者无疆",
                "v1.0",
                "http://localhost:8080/",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}

