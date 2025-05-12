package com.ecommerce.api.dto.request;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("permissions")
    private Set<Long> permissions;
}