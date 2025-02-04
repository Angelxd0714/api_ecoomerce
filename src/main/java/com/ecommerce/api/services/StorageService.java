package com.ecommerce.api.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobStorageException;
import com.ecommerce.api.config.AzureStorageConfig;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
@RequiredArgsConstructor
public class StorageService {

    @Value("${spring.cloud.azure.storage.account-name}")
    private String accountName;

    @Autowired
    private AzureStorageConfig azureStorageConfig;

    public String uploadFile(String fileName, File file) {
        try {
            // Obtén el cliente del contenedor y el blob
            BlobContainerClient containerClient = azureStorageConfig.blobServiceClient()
                    .getBlobContainerClient(accountName);
            BlobClient blobClient = containerClient.getBlobClient(fileName);

            // Verifica si el contenedor existe, si no, créalo
            if (!containerClient.exists()) {
                containerClient.create();
            }

            // Sube el archivo al blob
            blobClient.uploadFromFile(file.getAbsolutePath());

            // Devuelve la URL del blob
            return blobClient.getBlobUrl();
        } catch (BlobStorageException e) {
            throw new RuntimeException("Error al subir el archivo a Azure Blob Storage: " + e.getMessage(), e);
        }
    }


}
