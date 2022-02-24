package com.ssafy.BackEnd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//CORS오류 해결
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //모든 url요청 동작
                .allowedOrigins("*") //모든 도메인 허용
                .allowedMethods("*") //모든 메서드(get, post, put 등등) 허용
                .allowedHeaders("*") //모든 header name 허용
                .maxAge(3000); //preflight 요청 시간 설정
    }
}
