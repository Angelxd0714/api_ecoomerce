package com.ecommerce.api.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "history_bought")
public class HistoryBought {

    @Id
    private Long id;
    @OneToOne(targetEntity = Users.class)
    private Users user;
    @OneToOne(targetEntity = Product.class)
    private Product product;
    private LocalDate purchaseDate;
    private BigDecimal price;
    private int quantity;
    private BigDecimal total;
    @ManyToOne(targetEntity = Payments.class)
    private Payments paymentMethod;
    private String status;
    private String deliveryAddress;

    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }
}
