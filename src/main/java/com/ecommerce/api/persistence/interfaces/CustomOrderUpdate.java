package com.ecommerce.api.persistence.interfaces;

import com.ecommerce.api.persistence.entities.Orders;

public interface CustomOrderUpdate {
    void updateOrder(String id,Orders orders);
}
