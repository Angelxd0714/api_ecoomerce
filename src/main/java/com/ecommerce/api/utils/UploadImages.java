package com.ecommerce.api.utils;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class UploadImages {
    @Value("${file.upload-dir}")
    private String uploadDir;

    public String uploadImage(@Valid MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        try {
            // Crear directorio si no existe
            Files.createDirectories(Paths.get(uploadDir));

            // Guardar archivo
            String name = System.currentTimeMillis() + "-" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir, name);
            Files.write(path, file.getBytes());

            // Obtener URL base dinámica
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();

            // Devolver la URL completa de acceso
            return baseUrl + "/uploads/" + name;
        } catch (Exception e) {
            // Manejar excepción
            System.out.println(e.getMessage());
            log.error(String.valueOf(e.getCause()));
            throw new RuntimeException("Error uploading file", e);
        }
    }

}
