package com.ecommerce.api.controllers;

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



@CrossOrigin("*")
@RestController
@RequestMapping("api/category")
public class ControllerCategory {
    @Autowired
    private CategoryServices serviceCategory;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCategories() {
        try {
            System.out.println(serviceCategory.getAll());
            return ResponseEntity.ok(serviceCategory.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getCategoryOne/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(serviceCategory.getOne(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/createCategory")
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        try {
            System.out.println(category);
            serviceCategory.save(category);
            return ResponseEntity.ok(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<?> updateCategory(@RequestBody Category category, @PathVariable String id) {
        try {
            if(serviceCategory.getOne(id) == null){
              return ResponseEntity.badRequest().body("Category not found" + HttpStatus.BAD_REQUEST);
            }
            serviceCategory.update(category, id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        try {
            serviceCategory.delete(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + HttpStatus.BAD_REQUEST);
        }
    }
}
