package com.ecommerce.api.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ecommerce.api.dto.request.ProductRequest;
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
    public ResponseEntity<Map<String,String>> updateProduct(
            @PathVariable String id,
            @RequestPart("image") MultipartFile image,
           @RequestPart ProductRequest productRequest) throws IOException {
        try {
            productServices.update(productRequest, id, image);
            return ResponseEntity.ok(Map.of("message", "Actualización de producto exitosa."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al actualizar el producto", "detalle", e.getMessage()));
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
    public ResponseEntity<Map<String,String>> deleteProduct(@PathVariable String id) {
        try {
            productServices.delete(id);
            return ResponseEntity.ok(Map.of("message", "Eliminación de producto exitosa."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al eliminar el producto", "detalle", e.getMessage()));
        }
    }

    @GetMapping("/listProductByName/{name}")
    public ResponseEntity<Map<String,String>> listProductByName(@PathVariable List<String> name) {
        try {
            Map<String,String> response = new HashMap<>(Map.of("message", "Listado de productos exitoso."));
            response.put("productos", productServices.findByName(name).toString());
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
    public ResponseEntity<Map<String,String>> listProductByMarker(@PathVariable List<String> marker) {
        try {
            Map<String,String> response = new HashMap<>(Map.of("message", "Listado de productos exitoso."));
            response.put("productos", productServices.findByMarker(marker).toString());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al listar los productos", "detalle", e.getMessage()));
        }
    }
    @GetMapping("/listProductByPrice/{price}")
    public ResponseEntity<Map<String,String>> listProductByPrice(@PathVariable List<Double> price) {
        try {
            Map<String, String> response = new HashMap<>(Map.of("message", "Listado de productos exitoso."));
            response.put("productos", productServices.findByPrice(price).toString());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al listar los productos", "detalle", e.getMessage()));
        }
    }
}
