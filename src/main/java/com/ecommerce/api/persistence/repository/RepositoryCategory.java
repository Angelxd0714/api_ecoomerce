package com.ecommerce.api.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.persistence.entities.Category;

@Repository
public interface RepositoryCategory extends MongoRepository<Category,String> {
    
}
