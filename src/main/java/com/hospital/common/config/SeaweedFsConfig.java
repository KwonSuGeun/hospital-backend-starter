package com.hospital.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(SeaweedFsProperties.class)
public class SeaweedFsConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
