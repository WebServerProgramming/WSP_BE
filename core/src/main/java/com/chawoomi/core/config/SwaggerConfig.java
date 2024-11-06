package com.chawoomi.core.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "CHAWOOMI API 명세서",
                description = "CHAWOOMI BE API 명세서입니다.",
                version = "v1")
)
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi stOpenApi() {

        return GroupedOpenApi.builder()
                .group("CHAWOOMI API v1")
                .pathsToMatch("/**")
                .build();
    }
}

