package com.ecommerce.api.dto.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoriesRequest {
    private Long id;
    private String name;
    private String description;
}
