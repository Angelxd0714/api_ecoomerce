package com.ecommerce.api.services;

import java.math.BigDecimal;
import java.util.Collections;

import com.ecommerce.api.dto.request.OrdersRequest;
import com.ecommerce.api.dto.request.PaymentRequest;
import com.ecommerce.api.dto.request.UserRequest;
import com.mercadopago.MercadoPagoConfig;

import com.mercadopago.client.payment.*;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.payment.PaymentMethod;
import com.mercadopago.resources.preference.Preference;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;




@Service
@RequiredArgsConstructor
public class ServiceMercadoPago {
  @Value("${mercadopago.access-token}")
    private String accessToken;
  @Value("${mercadopago.access-key}")
    private String accessKey;
  public Pair<Boolean,Long> processPayment(PaymentRequest paymentRequest, String email, BigDecimal amount) {
    try {
      // Configurar el token antes de llamar a Mercado Pago
      MercadoPagoConfig.setAccessToken(accessToken);


      PaymentClient client = new PaymentClient();
      PaymentCreateRequest request = PaymentCreateRequest.builder()
              .transactionAmount(amount)
              .description("Pago de orden " + paymentRequest.getOrderId())
              .installments(1)
              .paymentMethodId(paymentRequest.getPaymentMethod())
              .payer(PaymentPayerRequest.builder().email(email).build())
              .build();

      Payment payment = client.create(request);

      if ("approved".equals(payment.getStatus())) {
        return Pair.of(true, payment.getId()); // Pago aprobado, devolver ID de transacción
      } else {
        return Pair.of(false, null); // Pago rechazado
      }
    }catch (Exception e) {
        e.printStackTrace();
        if (e instanceof MPApiException apiException) {
            System.err.println("❌ Error API MercadoPago: " + apiException.getApiResponse().getContent());
        }
        return Pair.of(false, null);
    }
  }
  }



