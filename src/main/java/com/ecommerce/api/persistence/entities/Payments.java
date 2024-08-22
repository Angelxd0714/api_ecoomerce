package com.ecommerce.api.persistence.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.DateOperators.DateAdd;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payments {
    @Id
    private String id;
    @Field("order_id")
    private Orders orderId;
    @Field("user_id")
    private Users userId;
    @Field("payment_method")
    private String paymentMethod;
    @Field("payment_amount")
    private Long paymentAmount;
    @Field("payment_status")
    private String paymentStatus;
    @Field("payment_currency")
    private String paymentCurrency;
    @Field("payment_date")
    private DateAdd paymentDate;
    @Field("transaction_id")
    private String transactionId;
    @Field("created_at")
    private DateAdd createdAt;
}
