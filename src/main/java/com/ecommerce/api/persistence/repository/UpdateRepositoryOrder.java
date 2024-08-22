package com.ecommerce.api.persistence.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.persistence.entities.Orders;
import com.ecommerce.api.persistence.interfaces.CustomOrderUpdate;
import org.springframework.data.mongodb.core.MongoTemplate;

@Repository
public class UpdateRepositoryOrder implements CustomOrderUpdate {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void updateOrder(String id, Orders orders) {
        Query query = new Query(Criteria.where("_id").is(id));

        Update update = new Update();

        if(orders.getStatus() != null){
            update.set("status", orders.getStatus());
        }
        if(orders.getUserId() != null){
            update.set("paymentMethod", orders.getUserId());
        }
        if(orders.getOrderDate()!=null){
            update.set("orderDate", orders.getOrderDate());
        }
        if(orders.getProductList() != null){
            update.set("productList", orders.getProductList());
        }
        if(orders.getTotalAmount() != null){
            update.set("totalAmount", orders.getTotalAmount());
        }
        mongoTemplate.updateFirst(query, update, Orders.class);
    }
    
}
