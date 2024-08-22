package com.ecommerce.api.persistence.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.persistence.entities.Category;
import com.ecommerce.api.persistence.interfaces.CustomCategoryPernalize;

@Repository
public class UpdateRepositoryPersonalize implements CustomCategoryPernalize {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void updateCategory(String id, Category category) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        if(category.getName() != null){
            update.set("name", category.getName());
        }
        if(category.getDescription() != null){
            update.set("description", category.getDescription());
        }
        mongoTemplate.updateFirst(query, update, Category.class);

    }
    
}
