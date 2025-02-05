package com.ecommerce.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.api.dto.request.RequestCreateUser;
import com.ecommerce.api.dto.request.RequestLogin;
import com.ecommerce.api.dto.response.Response;
import com.ecommerce.api.services.UserDetailServiceImpl;

import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("api/auth")
public class ControllerAuth {
     @Autowired
    UserDetailServiceImpl userDetailService;
    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody @Valid RequestLogin requestLogin){
        try {
            return new ResponseEntity<>(userDetailService.loginUser(requestLogin), HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
        @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody @Valid RequestCreateUser requestCreateUser){
        System.out.println(requestCreateUser);
        try {
            return new ResponseEntity<>(userDetailService.createUser(requestCreateUser), HttpStatus.OK);
        }catch (Exception e){

            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
