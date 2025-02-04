package com.ecommerce.api.config;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class AzureStorageConfig {



    @Value("${spring.cloud.azure.storage.account-key}")
    private String accountKey;

    @Value("${spring.cloud.azure.storage.blob.connection-string}")
    private String blobStringconexion;

    @Value("${spring.cloud.azure.storage.blob-endpoint}")
    private String blobEndpoint;


    @Bean
    public BlobServiceClient blobServiceClient() {
        return new BlobServiceClientBuilder()
                .connectionString(blobStringconexion)
                .endpoint(blobEndpoint)
                .sasToken(accountKey)
                .buildClient();
    }

}
