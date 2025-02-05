package com.ecommerce.api.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.*;

import lombok.*;


import java.time.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "products")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "stock")
    private Integer stock;
    @Column(name = "image")
    private String image;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_category", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")


    )
    private List<Category> categories;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Markers.class)
    private Markers marker;
    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;
}
