package com.ecommerce.api.utils;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadImages {
    @Value("file.upload-dir")
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

            // Devolver la URL de acceso
            return "/uploads/" + name;
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file", e);
        }
    }

}
