package com.ecommerce.api.dto;

import jakarta.validation.constraints.NotNull;

public record RequestCreateUser(@NotNull String username,@NotNull String password,@NotNull RequestCreateRol roles) {
 
    
}
