package com.ecommerce.api.services;

import java.math.BigDecimal;

import com.ecommerce.api.dto.request.PaymentRequest;


import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;



@RequiredArgsConstructor
@Service
public class StripePago {
  @Value("${stripe.access-token}")
    private String accessToken;
  @Value("${stripe.access-key}")
    private String accessKey;
    public Pair<Boolean,Long> processPayment(PaymentRequest paymentRequest, String email, BigDecimal amount) {
        Stripe.apiKey = accessToken;

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount.longValue() * 100)
                .setCurrency("ars")
                .setPaymentMethod(paymentRequest.getMpCardToken())
                .setConfirm(true)
                .setReceiptEmail(email)
                .build();
        try {
            PaymentIntent paymentIntent = PaymentIntent.create(params);
            if (paymentIntent.getStatus().equals("succeeded")) {
                return Pair.of(true, Long.valueOf(paymentIntent.getId()));
            } else {
                return Pair.of(false, Long.valueOf(paymentIntent.getId()));
            }
        } catch (Exception e) {
            System.err.println("❌ Error al procesar el pago: " + e.getMessage());
            return Pair.of(false, null);
        }
    }

}



