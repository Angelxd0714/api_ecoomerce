package com.ecommerce.api.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductRequest {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private List<CategoriesRequest> categoriesRequestLis = new ArrayList<>();
    private MarkersRequest marker = new MarkersRequest();

}
