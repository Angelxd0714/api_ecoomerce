package com.ecommerce.api.dto.request;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RolRequest {
    private Long id;
    private String name;

    @JsonProperty("permissions")
    private Set<Long> permissions = new HashSet<>();
}