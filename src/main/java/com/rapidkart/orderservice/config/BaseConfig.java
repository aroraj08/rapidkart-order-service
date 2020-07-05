package com.rapidkart.orderservice.config;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BaseConfig {

    @Bean
    @Autowired
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
