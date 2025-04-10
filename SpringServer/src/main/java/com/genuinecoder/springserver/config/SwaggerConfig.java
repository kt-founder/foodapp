package com.genuinecoder.springserver.config;



import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("GenuineCoder API")
                        .description("API documentation for GenuineCoder Spring Boot project")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("GenuineCoder")
                                .url("https://genuinecoder.com")
                                .email("contact@genuinecoder.com")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-apis")
                .pathsToMatch("/**")
                .build();
    }
}

