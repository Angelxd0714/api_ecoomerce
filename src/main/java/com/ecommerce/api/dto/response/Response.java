package com.ecommerce.api.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "message", "token", "status"})
public record Response(String username,
        String message,
        String token,
        Boolean status) {

}
