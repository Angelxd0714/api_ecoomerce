package com.ecommerce.api.persistence.interfaces;

import com.ecommerce.api.persistence.entities.Order;

public interface CustomOrderUpdate {
    void updateOrder(String id, Order orders);
}
