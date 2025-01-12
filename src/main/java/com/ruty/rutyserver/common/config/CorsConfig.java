package com.ruty.rutyserver.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 엔드포인트에 대해 CORS 허용
                .allowedOrigins("*")
                .exposedHeaders("Set-Cookie", "Authorization", "Authorization-refresh")
                .allowedMethods("*") // 허용할 메서드
                .allowedHeaders("*"); // 모든 헤더 허용
    }
}
