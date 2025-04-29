package com.ecommerce.api.controllers;

import com.ecommerce.api.dto.request.CarRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.api.persistence.entities.Car;
import com.ecommerce.api.persistence.entities.Users;
import com.ecommerce.api.services.CarServices;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("api/cart")
public class ControllerCar {
    @Autowired
    private CarServices carServices;


    @GetMapping("/all")
    public ResponseEntity<Map<String,Object>> getAllCar(){
         Map<String,Object> response = new HashMap<>();
         try {
            response.put("cars", carServices.findAll());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getCarById(@PathVariable Long id){
        Map<String,Object> response = new HashMap<>();
        try {
            response.put("car", carServices.findById(id));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping("/cartUser/{userId}")
    public ResponseEntity<Map<String,Object>> getCarByUserId(@PathVariable Long userId){
        Map<String,Object> response = new HashMap<>();
        try {
            response.put("car", carServices.getCart(userId));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Map<String,String>> clearCart(@PathVariable Long userId){
        Map<String,String> response = new HashMap<>();
        try {
            carServices.clearCart(userId);
            response.put("message", "Carrito eliminado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/save")
    public ResponseEntity<Map<String,String>> saveCar(@RequestBody CarRequest car){
        Map<String,String> response = new HashMap<>();
        try {
            carServices.addToCart(car);
            response.put("message", "Carrito creado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String,String>> deleteCar(@PathVariable Long id){
        Map<String,String> response = new HashMap<>();
        try {
            carServices.deleteById(id);
            response.put("message", "Carrito eliminado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping("/carUser/{userId}")
    public ResponseEntity<Map<String,Object>> getCarByUserId(@RequestBody Users userId){
         Map<String,Object> response = new HashMap<>();
        try {
            response.put("car", carServices.findByUserId(userId));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String,String>> updateCar(@PathVariable Long id, @RequestBody CarRequest car){
         Map<String,String> response = new HashMap<>();
        try {
            carServices.update(id, car);
            response.put("message", "Carrito actualizado correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
