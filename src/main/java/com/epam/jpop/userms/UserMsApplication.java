package com.epam.jpop.userms;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserMsApplication.class, args);


    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("User Service")
                        .description("Spring User service application")
                        .version("v1")
                        .description("User Service Documentation"));
    }

}
