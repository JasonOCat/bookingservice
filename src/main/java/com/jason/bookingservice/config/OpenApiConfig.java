package com.jason.bookingservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bookingServiceApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Booking Service API")
                        .description("Booking Service API for booking")
                        .version("v1.0.0")
                );
    }
}
