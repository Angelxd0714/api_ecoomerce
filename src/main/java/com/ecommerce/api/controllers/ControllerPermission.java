package com.ecommerce.api.controllers;

import com.ecommerce.api.dto.request.PermissionsRequest;
import com.ecommerce.api.services.PermissionServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import javax.security.auth.kerberos.ServicePermission;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/permission")
public class ControllerPermission {
    @Autowired

    private PermissionServices servicePermission;

    @GetMapping("/all")
    public ResponseEntity<Map<String,Object>> getAllPermissions(){
        Map<String,Object> response = new HashMap<>();
        try {
            response.put("permissions", servicePermission.getAll());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping("/one/{id}")
    public ResponseEntity<Map<String,Object>> getOnePermission(@PathVariable Long id){
        Map<String,Object> response = new HashMap<>();
        try {
            response.put("permission", servicePermission.getOne(id));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
@GetMapping("/delete/{id}")
public ResponseEntity<Map<String,String>> deletePermission(@PathVariable Long id) {
    Map<String, String> response = new HashMap<>();
    try {
        servicePermission.delete(id);
        response.put("message", "Permiso eliminado correctamente");
        return ResponseEntity.ok(response);
    } catch (Exception e) {
        response.put("error", e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

}
@PostMapping("/save")
public ResponseEntity<Map<String,String>> savePermission(@RequestBody PermissionsRequest permission) {
    Map<String, String> response = new HashMap<>();
    try {
        servicePermission.save(permission);
        response.put("message", "Permiso creado correctamente");
        return ResponseEntity.ok(response);
    } catch (Exception e) {
        response.put("error", e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
@PutMapping("/update/{id}")
public ResponseEntity<Map<String,String>> updatePermission(@PathVariable Long id, @RequestBody PermissionsRequest permission) {
    Map<String, String> response = new HashMap<>();
    try {
        servicePermission.update(permission, id);
        response.put("message", "Permiso actualizado correctamente");
        return ResponseEntity.ok(response);
    } catch (Exception e) {
        response.put("error", e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
}
