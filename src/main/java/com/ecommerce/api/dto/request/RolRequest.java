package com.ecommerce.api.dto.request;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolRequest {
    private Long id;
    private String name;
    private Set<PermissionsRequest> permissions = new HashSet<PermissionsRequest>();
}
