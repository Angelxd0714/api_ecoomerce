package com.ecommerce.api.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

import com.ecommerce.api.persistence.entities.Orders;


import java.time.*;

@Repository
public interface RepositoryOrder extends MongoRepository<Orders,String> {
    @Query("{ 'orderDate': { $in: ?0 } }")
    List<Orders> findByOrderDate(List<LocalDateTime> orderDate);
    @Query("{ 'status': { $in: ?0 } }")
    List<Orders> findByStatus(List<String> orderStatus);
    @Query("{ 'userId.userId': { $in: ?0 } }")
    List<Orders> findByUserId(List<String> userId);
}
