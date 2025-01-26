package com.ecommerce.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.api.persistence.entities.Users;
import com.ecommerce.api.services.UsersServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@CrossOrigin("*")
@RestController
@RequestMapping("api/user")
public class ControllerUser {
    @Autowired
    private UsersServices userDetailService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(){
        try {
            return ResponseEntity.ok(userDetailService.getAllUsers());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + HttpStatus.BAD_REQUEST);
        
        }
    }
    @GetMapping("/getUserOne/{id}")
    public ResponseEntity<?> getUserOne(@PathVariable Long id){
        try {
            return ResponseEntity.ok(userDetailService.getUserIdent(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + HttpStatus.BAD_REQUEST);

        }
    }
    @GetMapping("/getUserEmail/{email}")
    public ResponseEntity<?> getUserEmail(@PathVariable String email){
        try {
            return ResponseEntity.ok(userDetailService.getUserByEmail(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + HttpStatus.BAD_REQUEST);

        }
    }
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Users user){
        try {
            userDetailService.updateUser(user, id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + HttpStatus.BAD_REQUEST);

        }
    }
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody Users user){
        try {
            userDetailService.saveUser(user);
            return ResponseEntity.ok().body(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() + HttpStatus.BAD_REQUEST);

        }
    }
    
}
