package com.ecommerce.api.controllers;

import com.ecommerce.api.dto.request.MarkersRequest;
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

import com.ecommerce.api.persistence.entities.Markers;
import com.ecommerce.api.services.MarkersServices;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/marker")
public class ControllerMarker {
    @Autowired
    private MarkersServices serviceMarker;

    @GetMapping("/all")
    public ResponseEntity<Map<String,Object>> getAllMarkers(){
        Map<String,Object> response = new HashMap<>();
        try {
            response.put("markers", serviceMarker.findAll());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping("/getMarkerOne/{id}")
    public ResponseEntity<Map<String,Object>> getMarkerOne(@PathVariable Long id){
            Map<String,Object> response = new HashMap<>();
        try {
            response.put("marker", serviceMarker.findById(id));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/createMarker")
    public ResponseEntity<Map<String,String>> createMarker(@RequestBody MarkersRequest marker){
        Map<String,String> response = new HashMap<>();
        try {
            serviceMarker.save(marker);
            response.put("message", "Marker created successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PutMapping("/updateMarker/{id}")
    public ResponseEntity<Map<String,String>> updateMarker(@RequestBody MarkersRequest marker, @PathVariable String id){
        Map<String,String> response = new HashMap<>();
        try {
            serviceMarker.update(marker, Long.parseLong(id));
            response.put("message", "Marker updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @DeleteMapping("/deleteMarker/{id}")
    public ResponseEntity<Map<String,String>> deleteMarker(@PathVariable Long id){
        Map<String,String> response = new HashMap<>();
        try {
            serviceMarker.delete(id);
            response.put("message", "Marker deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
