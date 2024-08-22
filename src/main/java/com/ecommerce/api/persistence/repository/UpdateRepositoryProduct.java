package com.ecommerce.api.persistence.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.persistence.entities.Product;
import com.ecommerce.api.persistence.interfaces.CustomProductoPersonalizeUpdate;

@Repository
public class UpdateRepositoryProduct implements CustomProductoPersonalizeUpdate {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public void updateProductoPersonalize(String id, Product product) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        if(product.getName() != null){
            update.set("name", product.getName());
        }
        if(product.getDescription() != null){
            update.set("description", product.getDescription());
        }
        if(product.getPrice() != null){
            update.set("price", product.getPrice());
        }
        if(product.getStock() != null){
            update.set("stock", product.getStock());
        }
        if(product.getCategories() != null){
            update.set("category", product.getCategories());
        }
        if(product.getMarker() != null){
            update.set("marker", product.getMarker());
        }
        if(product.getImage() != null){
            update.set("image", product.getImage());
        }
        if(product.getCreatedAt() != null){
            update.set("createdAt", product.getCreatedAt());
        }
        if(product.getUpdatedAt() != null){
            update.set("updatedAt", product.getUpdatedAt());
        }
        mongoTemplate.updateFirst(query, update, Product.class);
    }
    
}
