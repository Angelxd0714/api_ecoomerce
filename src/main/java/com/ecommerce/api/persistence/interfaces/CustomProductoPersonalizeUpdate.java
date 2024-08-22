package com.ecommerce.api.persistence.interfaces;

import com.ecommerce.api.persistence.entities.Product;

public interface CustomProductoPersonalizeUpdate {
    void updateProductoPersonalize(String id,Product product);
}
