package com.ecommerce.api.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MarkersDTO {
    private Long id;
    private String name;
    private String description;
}
