package com.ecommerce.api.dto.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PermissionsRequest {
    private Long id;
    private String name;
}
