package com.ecommerce.api.persistence.repository;

import java.math.BigDecimal;
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
public interface RepositoryProduct extends CrudRepository<Product,Long> {
    List<Product> findAll();
    @Query("select p from products p where p.name in ?1")
    List<Product> findByName(List<String> names);
    @Query("SELECT p FROM products p JOIN p.categories c WHERE c IN :categories")
    List<Product> findByCategories(@Param("categories") List<Category> categories);
    @Query("select p from products p JOIN p.marker c where c.name in ?1")
    List<Product> findByMarker(String marker);
    @Query("select p from products p where p.price in ?1")
    List<Product> findByPrice(BigDecimal price);
    @Modifying
    @Transactional
    @Query("UPDATE products p SET " +
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
      @Query("select p from products p where p.id in ?1")
    List<Product> findByIds(List<Long> ids);
}
