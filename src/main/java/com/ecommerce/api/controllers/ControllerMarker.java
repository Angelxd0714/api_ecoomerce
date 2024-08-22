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

import com.ecommerce.api.persistence.entities.Markers;
import com.ecommerce.api.services.MarkersServices;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/marker")
public class ControllerMarker {
    @Autowired
    private MarkersServices serviceMarker;

    @GetMapping("/all")
    public ResponseEntity<?> getAllMarkers(){
        try {
            return ResponseEntity.ok(serviceMarker.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getMarkerOne/{id}")
    public ResponseEntity<?> getMarkerOne(@PathVariable String id){
        try {
            return ResponseEntity.ok(serviceMarker.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/createMarker")
    public ResponseEntity<?> createMarker(@RequestBody Markers marker){
        try {
            serviceMarker.save(marker);
            return ResponseEntity.ok(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/updateMarker/{id}")
    public ResponseEntity<?> updateMarker(@RequestBody Markers marker, @PathVariable String id){
        try {
            if(!serviceMarker.existsById(id)){
                return ResponseEntity.badRequest().body("Marker not found: " + HttpStatus.BAD_REQUEST);
            }
            serviceMarker.update(marker, id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/deleteMarker/{id}")
    public ResponseEntity<?> deleteMarker(@PathVariable String id){
        try {
            serviceMarker.delete(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + HttpStatus.BAD_REQUEST);
        }
    }
}
