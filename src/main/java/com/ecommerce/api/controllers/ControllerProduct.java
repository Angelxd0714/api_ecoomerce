package com.ecommerce.api.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.time.*;
import com.ecommerce.api.persistence.entities.Category;
import com.ecommerce.api.persistence.entities.Markers;
import com.ecommerce.api.persistence.entities.Product;
import com.ecommerce.api.services.ProductServices;

@CrossOrigin("*")
@RestController
@RequestMapping("api/product")
public class ControllerProduct {
    @Value("${file.upload-dir}")
    private String uploadDir;
    @Autowired
    private ProductServices productServices;

    @PostMapping("/createProduct")
    public ResponseEntity<?> createProduct(
            @RequestParam("image") MultipartFile image,
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam("stock") Integer stock,
            @RequestParam(value = "categories", required = false) List<Category> categories,
            @RequestParam(value = "marker", required = false) Markers markerId,
            @RequestParam(value = "createdAt", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdAt,
            @RequestParam(value = "updateAt", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime updateAt)
            throws IOException {

        String uploadDir = this.uploadDir;
        String fileName = StringUtils.cleanPath(UUID.randomUUID().toString() + image.getOriginalFilename());
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try {
            Files.copy(image.getInputStream(), uploadPath.resolve(fileName));
            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/upload/images/")
                    .path(fileName)
                    .toUriString();

            // Manejo de categorías y marcador

            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStock(stock);
            product.setImage(imageUrl);
            product.setCategories(categories);
            product.setMarker(markerId);
            product.setCreatedAt(createdAt);
            product.setUpdatedAt(updateAt);

            productServices.save(product);
            return ResponseEntity.ok().body("Creación de producto exitosa.");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error al subir el archivo: " + e.getMessage());
        }
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable String id,
            @RequestParam("image") MultipartFile image,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "stock", required = false) Integer stock,
            @RequestParam(value = "categories", required = false) List<Category> categories,
            @RequestParam(value = "marker", required = false) Markers markerId,
            @RequestParam(value = "createdAt", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdAt,
            @RequestParam(value = "updateAt", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime updateAt) {
        Product product = productServices.findById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        // Si se proporciona una nueva imagen, maneja el reemplazo de la anterior
        if (image != null && !image.isEmpty()) {
            try {
                String oldImageUrl = product.getImage();
                if (oldImageUrl != null) {
                    Path oldImagePath = Paths.get(oldImageUrl);
                    if (Files.exists(oldImagePath)) {
                        Files.delete(oldImagePath);
                    }
                }

                String uploadDir = this.uploadDir + "/";
                String fileName = StringUtils.cleanPath(UUID.randomUUID().toString() + image.getOriginalFilename());
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Files.copy(image.getInputStream(), uploadPath.resolve(fileName));
                String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path(uploadDir)
                        .path(fileName)
                        .toUriString();
                System.out.println(imageUrl);

                product.setImage(imageUrl);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error a subir el archivo: " + e.getMessage());
            }
        }

        // Actualiza los demás campos
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategories(categories);
        product.setMarker(markerId);
        product.setCreatedAt(createdAt);
        product.setUpdatedAt(updateAt);

        productServices.update(product, id);

        return ResponseEntity.ok().body("Producto actualizo!");
    }

    @GetMapping("/listProduct")
    public ResponseEntity<?> listProduct() {
        try {
            return ResponseEntity.ok(productServices.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/listProduct/{id}")
    public ResponseEntity<?> listProductById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(productServices.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        try {
            productServices.delete(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/listProductByName/{name}")
    public ResponseEntity<?> listProductByName(@PathVariable List<String> name) {
        try {
            return ResponseEntity.ok(productServices.findByName(name));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/listProductByCategory/{category}")
    public ResponseEntity<?> listProductByCategory(@PathVariable List<String> category) {
        try {
            return ResponseEntity.ok(productServices.findByCategory(category));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/listProductByMarker/{marker}")
    public ResponseEntity<?> listProductByMarker(@PathVariable List<String> marker) {
        try {
            return ResponseEntity.ok(productServices.findByMarker(marker));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/listProductByPrice/{price}")
    public ResponseEntity<?> listProductByPrice(@PathVariable List<Double> price) {
        try {
            return ResponseEntity.ok(productServices.findByPrice(price));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + HttpStatus.BAD_REQUEST);
        }
    }
}
