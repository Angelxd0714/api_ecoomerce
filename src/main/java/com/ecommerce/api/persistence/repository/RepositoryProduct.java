package com.ecommerce.api.persistence.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.persistence.entities.Category;
import com.ecommerce.api.persistence.entities.Markers;
import com.ecommerce.api.persistence.entities.Product;

@Repository
public interface RepositoryProduct extends MongoRepository<Product,String> {
    List<Product> findAll();
    @Query("{ 'name': { $in: ?0 } }")
    Iterable<Product> findByName(List<String> names);
    @Query("{ 'categories.name': { $in: ?0 } }")
    Iterable<Product> findByCategories(List<String> category);
    @Query("{ 'marker.name': { $in: ?0 } }")
    Iterable<Product> findByMarker(List<String> marker);
    @Query("{ 'price': { $in: ?0 } }")
    Iterable<Product> findByPrice(List<Double> price);
}
