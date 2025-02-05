package com.ecommerce.api.dto.request;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

@Validated
public record RequestLogin(@NotNull String username,@NotNull String password) {

    
}
