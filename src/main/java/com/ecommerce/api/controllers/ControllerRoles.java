package com.ecommerce.api.controllers;

import com.ecommerce.api.dto.request.RolRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.api.persistence.entities.Roles;
import com.ecommerce.api.services.RolesServices;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("api/roles")
public class ControllerRoles {
    @Autowired
    private RolesServices rolesServices;

    @GetMapping("/all")
    public ResponseEntity<Map<String,Object>> getAllRoles(){
        Map<String,Object> response = new HashMap<>();
        try {
            response.put("roles", rolesServices.findAll());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping("/getRol/{id}")
    public ResponseEntity<Map<String,Object>> getRoleById(String id){
       Map<String,Object> response = new HashMap<>();
        try {
            response.put("role", rolesServices.findById(Long.parseLong(id)));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<?> createRole(RolRequest roles){
        Map<String,String> response = new HashMap<>();
        try {
            rolesServices.save(roles);
            response.put("message", "Rol creado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String,String>> updateRole(Long id, RolRequest roles){
         Map<String,String> response = new HashMap<>();
        try {
            rolesServices.update(roles, id);
            response.put("message", "Rol actualizado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String,String>> deleteRole(Long id){
         Map<String,String> response = new HashMap<>();
        try {
            rolesServices.delete(id);
            response.put("message", "Rol eliminado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
