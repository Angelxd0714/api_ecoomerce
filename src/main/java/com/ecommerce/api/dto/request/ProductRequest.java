package com.ecommerce.api.dto.request;

import lombok.*;

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
    private Double price;
    private Integer stock;
    private List<CategoriesRequest> categoriesRequestLis = new ArrayList<>();
    private MarkersRequest marker = new MarkersRequest();

}
