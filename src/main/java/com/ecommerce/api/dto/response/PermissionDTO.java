package com.ecommerce.api.dto.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PermissionDTO {
    private Long id;
    private String name;
}
