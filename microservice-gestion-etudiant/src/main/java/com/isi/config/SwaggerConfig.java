package com.isi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket postsApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("isi")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.isi"))
                .paths(regex("/gestion-etudiants.*"))
                .build()
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()));

    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("Documentation Gestion des etudiants")
                .description("Microservice etudiant est un API qui s'occupe de la Gestion des etudiants, " +
                        " etc.")
                .termsOfServiceUrl("https://www.ici.com")
                .license("dioufserignemor")
                .licenseUrl("https://www.diouf.com")
                .version("1.0")
                .contact(new Contact("diouf", "https://www.diouf.com", "diouf@gmail.com"))
                .build();

    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference("JWT", new AuthorizationScope[0])))
                .build();
    }
}
