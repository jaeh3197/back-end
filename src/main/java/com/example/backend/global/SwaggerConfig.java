package com.example.backend.global;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .title("Back-End API")       // API 문서 제목
                .version("v1.0.0")          // API 문서 버전
                .description("백엔드 API"); // API 문서 설명

        return new OpenAPI()
                .info(info);
    }
}
