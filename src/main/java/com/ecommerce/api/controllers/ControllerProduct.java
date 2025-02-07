package com.ecommerce.api.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ecommerce.api.dto.request.ProductRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.api.services.ProductServices;

@CrossOrigin("*")
@RestController
@RequestMapping("api/product")
@Slf4j
public class ControllerProduct {

    @Autowired
    private ProductServices productServices;

    @PostMapping(value = "/createProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> createProduct(
            @RequestPart("image") MultipartFile image,
            @RequestPart("productRequest") ProductRequest productRequest) {
        try {
            productServices.save(productRequest, image);
            return ResponseEntity.ok(Map.of("message", "Creación de producto exitosa."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al crear el producto", "detalle", e.getMessage()));
        }
    }
    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<Map<String, String>> updateProduct(
            @PathVariable Long id,
            @RequestPart("image") MultipartFile image,
            @RequestPart ProductRequest productRequest) {
        try {
            // Validate input parameters
            if (id == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Product ID cannot be null"));
            }

            if (image == null || image.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Image file is required"));
            }

            if (productRequest == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Product request data is required"));
            }

            // Validate image type
            String contentType = image.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Invalid file type. Only images are allowed"));
            }

            productServices.update(productRequest, id, image);
            return ResponseEntity.ok(Map.of("message", "Product updated successfully"));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Product not found", "detail", e.getMessage()));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Error processing image file", "detail", e.getMessage()));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid input data", "detail", e.getMessage()));

        } catch (Exception e) {
            log.error("Unexpected error during product update", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred",
                            "detail", "Please contact support if the problem persists"));
        }


}

    @GetMapping("/listProduct")
    public ResponseEntity<Map<String, Object>> listProduct() { // Cambia a Map<String, Object>
        try {
            Map<String, Object> response = new HashMap<>(); // Simplifica la creación del Map
            response.put("message", "Listado de productos exitoso.");
            response.put("productos", productServices.findAll()); // Devuelve la lista directamente
            return ResponseEntity.ok(response); // No necesitas body() si ya tienes el Map
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al listar los productos", "detalle", e.getMessage()));
        }
    }


    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Map<String,String>> deleteProduct(@PathVariable Long id) {
        try {
            productServices.delete(id);
            return ResponseEntity.ok(Map.of("message", "Eliminación de producto exitosa."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al eliminar el producto", "detalle", e.getMessage()));
        }
    }

    @GetMapping("/listProductByName/{name}")
    public ResponseEntity<Map<String,Object>> listProductByName(@PathVariable String name) {
        try {
            Map<String,Object> response = new HashMap<>(Map.of("message", "Listado de productos exitoso."));
            response.put("productos", productServices.findByName(name));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al listar los productos", "detalle", e.getMessage()));
        }
    }

    @GetMapping("/listProductByCategory/{category}")
    public ResponseEntity<Map<String,Object>> listProductByCategory(@PathVariable String category) {
        try {
            Map<String,Object> response = new HashMap<>(Map.of("message", "Listado de productos exitoso."));
            response.put("productos", productServices.findByCategory(category));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al listar los productos", "detalle", e.getMessage()));
        }
    }

    @GetMapping("/listProductByMarker/{marker}")
    public ResponseEntity<Map<String,Object>> listProductByMarker(@PathVariable String marker) {
        try {
            Map<String,Object> response = new HashMap<>(Map.of("message", "Listado de productos exitoso."));
            response.put("productos", productServices.findByMarker(marker));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al listar los productos", "detalle", e.getMessage()));
        }
    }
    @GetMapping("/listProductByPrice/{price}")
    public ResponseEntity<Map<String,Object>> listProductByPrice(@PathVariable BigDecimal price) {
        try {
            Map<String, Object> response = new HashMap<>(Map.of("message", "Listado de productos exitoso."));
            response.put("productos", productServices.findByPrice(price));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al listar los productos", "detalle", e.getMessage()));
        }
    }
    @GetMapping("/listProductById/{id}")
    public ResponseEntity<Map<String,Object>> listProductById(@PathVariable Long id) {
        try {
            Map<String, Object> response = new HashMap<>(Map.of("message", "Listado de productos exitoso."));
            response.put("productos", productServices.findById(id));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al listar los productos", "detalle", e.getMessage()));
        }
    }

}
