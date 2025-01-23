package com.ecommerce.api.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.persistence.entities.Category;

@Repository
public interface RepositoryCategory extends CrudRepository<Category,String> {
    @Query("UPDATE Category c SET c.categoryName = ?2 WHERE c.categoryId = ?1")
    void updateCategory(String categoryId, Category categoryName);
}
