package org.example.lab1_web_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestClient;

@SpringBootApplication
@EnableRetry
public class Lab1WebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab1WebServiceApplication.class, args);
    }

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }
}
