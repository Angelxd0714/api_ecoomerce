package com.ecommerce.api.dto.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MarkersRequest {
    private String name;
    private String description;
}
