package com.ruty.rutyserver.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        // Security 설정: JWT 기반 인증
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP) // HTTP 기반 인증
                .scheme("bearer") // bearer 토큰 방식
                .bearerFormat("JWT") // JWT 형식으로 설정
                .in(SecurityScheme.In.HEADER) // Authorization 헤더에서 토큰을 받음
                .name("Authorization"); // 헤더 이름 설정

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                // 배포할때는 server주소 지정. -> 아니면 cors에러
                .servers(Collections.singletonList(new Server().url("https://ssong.store").description("API Server")))
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .security(Arrays.asList(securityRequirement))
                .info(apiInfo()) // API 정보 설정
                .openapi("3.0.0"); // OpenAPI 버전 설정
    }

    private Info apiInfo() {
        return new Info()
                .title("Ruty API Docs")
                .description("Swagger에서는 간편하게 API 테스트를 수행할 수 있도록 구성할 생각이며, API의 특이사항은 이곳에 작성해두겠습니다." +
                        "<br>모든 요청이 토큰을 기반으로 동작하기에 테스트하실때 꼭 토큰을 추가해주세요." +
                        "<br>(소셜 로그인은 Swagger에 따로 표시되지 않으니 이곳을 참고해주시면 감사하겠습니다.)")
                .version("1.0.0");
    }
}
