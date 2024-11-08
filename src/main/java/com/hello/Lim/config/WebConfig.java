package com.hello.Lim.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /img/** 경로로 들어오는 요청은 classpath:/hello/boot/img/에서 파일을 찾도록 설정
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:///C:/Class/bootimg/");
    }
}
