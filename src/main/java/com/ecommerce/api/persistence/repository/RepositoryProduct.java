package com.ecommerce.api.persistence.repository;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.persistence.entities.Category;
import com.ecommerce.api.persistence.entities.Markers;
import com.ecommerce.api.persistence.entities.Product;

@Repository
public interface RepositoryProduct extends CrudRepository<Product,String> {
    List<Product> findAll();
    @Query("select p from product p where p.name in ?1")
    Iterable<Product> findByName(List<String> names);
    @Query("SELECT p FROM product p WHERE p.categories IN ?1")
    Iterable<Product> findByCategories(List<String> category);
    @Query("select p from product p where p.marker in ?1")
    Iterable<Product> findByMarker(List<String> marker);
    @Query("select p from product p where p.price in ?1")
    Iterable<Product> findByPrice(List<Double> price);
    @Modifying
    @Transactional
    @Query("UPDATE product p SET " +
            "p.name = :#{#product.name}, " +
            "p.description = :#{#product.description}, " +
            "p.price = :#{#product.price}, " +
            "p.stock = :#{#product.stock}, " +
            "p.image = :#{#product.image}, " +
            "p.marker = :#{#product.marker}, " +
            "p.createdAt = :#{#product.createdAt}, " +
            "p.updatedAt = :#{#product.updatedAt}, " +
            "p.order = :#{#product.order} " +
            "WHERE p.id = :id")
    void updateProductoPersonalize(@Param("id") Long id, @Param("product") Product product);

}
