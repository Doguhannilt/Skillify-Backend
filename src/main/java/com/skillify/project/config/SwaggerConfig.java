package com.skillify.project.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Skillify API")
                        .version("1.0")
                        .description("GITHUB: github.com/doguhannilt")
                        .description("Skillify platform API Doc - Doguhannilt@gmail.com"));
    }
}
