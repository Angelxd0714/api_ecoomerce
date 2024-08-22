package com.ecommerce.api.persistence.interfaces;

import com.ecommerce.api.persistence.entities.Category;

public interface CustomCategoryPernalize {
    void updateCategory(String id,Category category);
}
