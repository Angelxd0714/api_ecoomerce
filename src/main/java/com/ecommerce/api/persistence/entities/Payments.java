package com.ecommerce.api.persistence.entities;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "payments")
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_id")
    private Order orderId;
    @Column(name = "user_id")
    private Users userId;
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "payment_amount")
    private Long paymentAmount;
    @Column(name = "payment_status")
    private String paymentStatus;
    @Column(name = "payment_currency")
    private String paymentCurrency;
    @Column(name = "payment_date")
    private LocalDate paymentDate;
    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "created_at")
    private LocalDate createdAt;
}
