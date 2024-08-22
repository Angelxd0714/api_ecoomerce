package com.ecommerce.api.persistence.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.persistence.entities.Markers;
import com.ecommerce.api.persistence.interfaces.CustomMarkersRepository;

@Repository
public class UpdatePersonalizeMarker implements CustomMarkersRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void updateMarker(String id, Markers updateRequest) {
        Query query = new Query(Criteria.where("_id").is(id));

        Update update = new Update();

        if(updateRequest.getName() != null) {
            update.set("name", updateRequest.getName());
        }
        if(updateRequest.getDescription() != null) {
            update.set("description", updateRequest.getDescription());
        }
        if(updateRequest.getCreatedAt() != null) {
            update.set("createdAt", updateRequest.getCreatedAt());
        }
        if(updateRequest.getUpdatedAt() != null) {
            update.set("updatedAt", updateRequest.getUpdatedAt());
        }
        if(!update.getUpdateObject().isEmpty()){
            mongoTemplate.updateFirst(query,update,Markers.class);
        }
      
    }

   
}
