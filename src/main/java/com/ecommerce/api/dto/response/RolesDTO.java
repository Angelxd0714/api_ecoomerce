package com.ecommerce.api.dto.response;

import com.ecommerce.api.dto.request.PermissionsRequest;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolesDTO {
    private Long id;
    private String name;
    private Set<PermissionDTO> permissions = new HashSet<PermissionDTO>();
}
