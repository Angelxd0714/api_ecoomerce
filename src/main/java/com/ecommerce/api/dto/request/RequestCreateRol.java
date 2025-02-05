package com.ecommerce.api.dto.request;

import java.util.List;

import org.springframework.validation.annotation.Validated;


import jakarta.validation.constraints.Size;

@Validated
public record RequestCreateRol(@Size(message = "maximo dos roles")List<String>rolesName) {
    
}
