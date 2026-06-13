package com.example.notesAPI.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

//this allows swagger-ui to render a field for auth in the endpoints, if the value in the field "name" is chnage, all
// @SecurityRequirement tags must be updated as well
@SecurityScheme(
        name = "JwtAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT")

public class SpringDocOpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Notes Web App API")
                        .version("1.0")
                        .description("API documentation for my notes web app"));
    }
}