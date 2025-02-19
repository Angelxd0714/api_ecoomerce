package com.ecommerce.api.services;

import java.math.BigDecimal;
import java.util.Collections;

import com.ecommerce.api.dto.request.OrdersRequest;
import com.mercadopago.MercadoPagoConfig;

import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;

import com.mercadopago.resources.preference.Preference;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;




@Service
@RequiredArgsConstructor
public class ServiceMercadoPago {
  @Value("${mercadopago.access-token}")
    private String accessToken;
  @Value("${mercadopago.access-key}")
    private String accessKey;

  public String createPaymentReference(OrdersRequest ordersRequest,
                                       Integer quantity,String categoryId)  {
    MercadoPagoConfig.setAccessToken(accessToken);
    PreferenceItemRequest item = PreferenceItemRequest.builder()
            .title("Compra")
            .quantity(quantity)
            .categoryId(categoryId)
            .unitPrice(ordersRequest.getTotalAmount())
            .build();
    PreferenceRequest preferenceRequest = PreferenceRequest.builder()
            .items(Collections.singletonList(item))
            .build();
    try {
      PreferenceClient client = new PreferenceClient();
      Preference preference = client.create(preferenceRequest);
      return preference.getId();
    } catch (Exception e) {
      throw new RuntimeException("Error en Mercado Pago: " + e.getMessage());
    }
  }
  }



