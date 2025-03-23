package com.BD.Demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Demo Inventario API")
                        .version("1.0")
                        .description("API Documentation for Demo Inventario")
                        .contact(new Contact()
                                .name("Demo Team")
                                .email("contact@demo.com")));
    }
}