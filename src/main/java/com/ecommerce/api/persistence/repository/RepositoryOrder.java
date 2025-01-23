package com.ecommerce.api.persistence.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

import com.ecommerce.api.persistence.entities.Orders;


import java.time.*;

@Repository
public interface RepositoryOrder extends CrudRepository<Orders,String> {
    @Query("SELECT * FROM Orders WHERE orderDate = ?0")
    List<Orders> findByOrderDate(List<LocalDateTime> orderDate);
    @Query("SELECT * FROM Orders WHERE orderStatus = ?0")
    List<Orders> findByStatus(List<String> orderStatus);
    @Query("SELECT * FROM Orders WHERE userId = ?0")
    List<Orders> findByUserId(List<String> userId);
    @Modifying
    @Transactional
    @Query("UPDATE Orders SET order.status = ?1 WHERE id = ?0")
    void updateOrder(Long id, Orders order);
}
