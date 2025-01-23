package com.ecommerce.api.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "history_seld")
public class HistorySeld {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Users sellerId;
    @ManyToOne(targetEntity = Product.class)
    private Product product;
    private BigDecimal quantitySold;
    private BigDecimal totalSold;
    private String status;
    private LocalDate soldDate;
    @ManyToOne(targetEntity = Users.class)
    private  Users buyerId;




}
