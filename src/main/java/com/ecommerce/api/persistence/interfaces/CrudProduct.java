package com.ecommerce.api.persistence.interfaces;

import java.util.List;

import com.ecommerce.api.persistence.entities.Category;
import com.ecommerce.api.persistence.entities.Markers;
import com.ecommerce.api.persistence.entities.Product;

public interface CrudProduct {
    void save(Product product);
    Product findById(String id);
    void update(Product product,String id);
    void delete(String id);
    Iterable<Product> findAll();
    Iterable<Product> findByName(List<String> name);
    Iterable<Product> findByCategory(List<String> category);
    Iterable<Product> findByMarker(List<String> marker);
    Iterable<Product> findByPrice(List<Double> price);
}
