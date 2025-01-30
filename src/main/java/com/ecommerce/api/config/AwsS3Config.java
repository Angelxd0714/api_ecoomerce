package com.ecommerce.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class AwsS3Config {





    @Value("${azure.storage.connection-string}")
    private String connectionString;

}
