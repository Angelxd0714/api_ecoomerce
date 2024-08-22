package com.ecommerce.api.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.model.climate.Order;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;

@Service
public class ServiceStripe {
    @Value("${stripe.api.key}")
    private String stripeSecretKey;
    @PostConstruct
    public void init(){
        Stripe.apiKey = stripeSecretKey;
    }
    public PaymentIntent createPaymentIntent(Order order) throws Exception{
        Map<String,Object> parms = new HashMap<>();
        parms.put("amount", order.getAmountTotal());
        parms.put("currency", order.getCurrency());
        parms.put("description", "Payment for order: " + order.getId());
        parms.put("status", order.getStatus());
        return PaymentIntent.create(parms);
    }
}
