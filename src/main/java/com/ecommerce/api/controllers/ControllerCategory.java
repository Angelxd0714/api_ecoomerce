package com.ecommerce.api.controllers;

import com.ecommerce.api.dto.request.CategoriesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.api.persistence.entities.Category;
import com.ecommerce.api.services.CategoryServices;

import java.util.HashMap;
import java.util.Map;


@CrossOrigin("*")
@RestController
@RequestMapping("api/category")
public class ControllerCategory {
    @Autowired
    private CategoryServices serviceCategory;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllCategories() {
        Map<String, Object> response = new HashMap<>(Map.of("message", "Listado de categorias exitoso."));
        response.put("categorias", serviceCategory.getAll());
        try {

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al listar las categorias");
            errorResponse.put("detalle", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/getCategoryOne/{id}")
    public ResponseEntity<Map<String, Object>> getCategoryById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>(Map.of("message", "Listado de categorias exitoso."));
        response.put("categorias", serviceCategory.getOne(id));
        try {
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al listar las categorias");
            errorResponse.put("detalle", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/createCategory")
    public ResponseEntity<Map<String, String>> createCategory(@RequestBody CategoriesRequest category) {
        Map<String, String> response = new HashMap<>();
        try {
            serviceCategory.save(category);
            response.put("message", "Category created successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Error creating category");
            response.put("detail", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<Map<String, String>> updateCategory(@RequestBody CategoriesRequest category, @PathVariable Long id) {
            try {
                serviceCategory.update(category, id);
                return ResponseEntity.ok(Map.of("message", "Category updated successfully"));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(Map.of("error", "Error updating category", "detail", e.getMessage()));
            }
    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<Map<String, String>> deleteCategory(@PathVariable Long id) {
        try {
            serviceCategory.delete(id);
            return ResponseEntity.ok(Map.of("message", "Category deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error deleting category", "detail", e.getMessage()));
        }

    }

    @GetMapping("/getCategoryByName/{name}")
    public ResponseEntity<Map<String, Object>> getCategoryByName(@PathVariable String name) {
        try {
            Map<String, Object> response = new HashMap<>(Map.of("message", "Listado de categorias exitoso."));
            response.put("categorias", serviceCategory.getOneByName(name));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al listar las categorias");
            errorResponse.put("detalle", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
