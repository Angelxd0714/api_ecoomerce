package com.ecommerce.api.persistence.repository;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.persistence.entities.Category;
import com.ecommerce.api.persistence.entities.Markers;
import com.ecommerce.api.persistence.entities.Product;

@Repository
public interface RepositoryProduct extends CrudRepository<Product,String> {
    List<Product> findAll();
    @Query("select p from Product p where p.name in ?1")
    Iterable<Product> findByName(List<String> names);
    @Query("SELECT p FROM Product p WHERE p.categories IN ?1")
    Iterable<Product> findByCategories(List<String> category);
    @Query("select p from Product p where p.marker in ?1")
    Iterable<Product> findByMarker(List<String> marker);
    @Query("select p from Product p where p.price in ?1")
    Iterable<Product> findByPrice(List<Double> price);
    @Modifying
    @Transactional
    @Query("UPDATE Product SET product = ?1 WHERE id = ?0")
    void updateProductoPersonalize(Long id, Product product);
}
