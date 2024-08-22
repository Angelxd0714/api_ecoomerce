package com.ecommerce.api.persistence.repository;

import java.util.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.persistence.entities.Markers;


@Repository
public interface RepositoryMarkers extends MongoRepository<Markers,String> {
    Optional<Markers> findByName(String name);

   

}
