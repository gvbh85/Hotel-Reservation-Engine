package com.hotel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI hotelReservationAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("Hotel Reservation System API")

                        .version("1.0")

                        .description("Spring Boot Hotel Reservation Backend")

                        .contact(new Contact()

                                .name("Hari")

                                .email("hari@gmail.com")));

    }

}