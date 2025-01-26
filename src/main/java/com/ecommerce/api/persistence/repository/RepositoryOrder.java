package com.ecommerce.api.persistence.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

import com.ecommerce.api.persistence.entities.Orders;


import java.time.*;

@Repository
public interface RepositoryOrder extends CrudRepository<Orders,String> {

        @Query("SELECT o FROM orders o WHERE o.orderDate = :orderDate")
        List<Orders> findByOrderDate(@Param("orderDate") LocalDateTime orderDate);

        @Query("SELECT o FROM orders o WHERE o.status IN :statuses")
        List<Orders> findByStatus(@Param("statuses") List<String> orderStatus);

        @Query("SELECT o FROM orders o WHERE o.user IN :userIds")
        List<Orders> findByUserId(@Param("userIds") List<String> userId);

        @Modifying
        @Transactional
        @Query("UPDATE orders o SET o.status = :status WHERE o.id = :id")
        void updateOrder(@Param("id") Long id, @Param("status") String status);


}
