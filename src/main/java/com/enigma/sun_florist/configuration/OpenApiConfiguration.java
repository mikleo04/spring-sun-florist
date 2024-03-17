package com.enigma.sun_florist.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customizeOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Sun Florist RESTfulAPI")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Hafid Siraj Aurakhmah Witra")
                                .email("hafidsiraj04aw@gmail.com")
                                .url("https://github.com/mikleo04"))
                        .description("""
                                     Sun Florist RESTful Api is a backend application that provides endpoints
                                     for managing customers, flowers with image uploads, downloads, and transactions.
                                     it follows RESTful principles, uses HTTP methods and status codes for communication
                                     and uses JSON as the main data exchange format. The json response provided is also
                                     consistently wrapped using Response Entity and DTO
                                     """))
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("Development"));
    }

}
