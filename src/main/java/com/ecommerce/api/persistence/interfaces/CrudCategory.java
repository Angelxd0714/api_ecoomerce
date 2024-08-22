package com.ecommerce.api.persistence.interfaces;

import com.ecommerce.api.persistence.entities.Category;

public interface CrudCategory {

    void save(Category category);

    void delete(String id);

    Category getOne(String id);

    Iterable<Category> getAll();

    void update(Category category,String id);
}
