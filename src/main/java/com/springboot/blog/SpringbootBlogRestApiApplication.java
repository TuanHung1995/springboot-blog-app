package com.springboot.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition (
        info = @Info(
                title = "Spring Boot Blog REST API",
                version = "1.0",
                description = "API for Spring Boot Blog Application",
                contact = @Contact(
                        name = "Tuan Hung Nguyen",
                        email = "tuanhungg1995@gmail.com",
                        url = ""
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Spring Boot Blog Documentation",
                url = "https://github.com/tuanhungg1995/springboot-blog-app"
        )
)
public class SpringbootBlogRestApiApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
    }

}
