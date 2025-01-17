package com.ecommerce.api.dto.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoriesRequest {
    private String name;
    private String description;
}
