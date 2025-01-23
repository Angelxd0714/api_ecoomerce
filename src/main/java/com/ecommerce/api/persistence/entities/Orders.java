package com.ecommerce.api.persistence.entities;

import jakarta.persistence.*;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.*;
import java.time.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @Column(name = "order_date")

    private LocalDateTime orderDate;

    @Column(name = "status")
    private String status;

    @OneToMany(targetEntity = Product.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private List<Product> productList;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;
}