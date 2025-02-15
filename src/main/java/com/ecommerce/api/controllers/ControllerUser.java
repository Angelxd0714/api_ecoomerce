package com.ecommerce.api.controllers;

import com.ecommerce.api.dto.request.UserRequest;
import com.ecommerce.api.services.UserDetailServiceImpl;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.api.persistence.entities.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;


@CrossOrigin("*")
@RestController
@RequestMapping("api/user")
public class ControllerUser {
    @Autowired
    private UserDetailServiceImpl userDetailService;

    @GetMapping("/all")
    public ResponseEntity<Map<String,Object>> getAllUsers(){
       Map<String,Object> response = new HashMap<>(Map.of("message", "Listado de usuarios exitoso."));
        try {
            response.put("users", userDetailService.getAllUsers());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al listar los usuarios", "detalle", e.getMessage()));
        }
    }
    @GetMapping("/getUserOne/{id}")
    public ResponseEntity<Map<String,Object>> getUserOne(@PathVariable Long id){
      Map<String,Object> response = new HashMap<>(Map.of("message", "Listado de usuarios exitoso."));
        try {
            response.put("users", userDetailService.getUserById(id));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al listar los usuarios", "detalle", e.getMessage()));
        }
    }
    @GetMapping("/getUserEmail/{email}")
    public ResponseEntity<Map<String,Object>> getUserEmail(@PathVariable String email){
        Map<String,Object> response = new HashMap<>(Map.of("message", "Listado de usuarios exitoso."));
        try {
            response.put("users", userDetailService.getUserByEmail(email));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al listar los usuarios", "detalle", e.getMessage()));
        }
    }
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<Map<String,Object>> updateUser(@PathVariable Long id, @RequestBody UserRequest user){
        Map<String,Object> response = new HashMap<>(Map.of("message", "Usuario actualizado exitosamente."));
        try {
            userDetailService.updateUser(user, id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al actualizar el usuario", "detalle", e.getMessage()));
        }
    }

    
}
