package com.ecommerce.api.persistence.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

import com.ecommerce.api.persistence.entities.Order;


import java.time.*;

@Repository
public interface RepositoryOrder extends CrudRepository<Order,String> {

        @Query("SELECT o FROM order o WHERE o.orderDate = :orderDate")
        List<Order> findByOrderDate(@Param("orderDate") LocalDateTime orderDate);

        @Query("SELECT o FROM order o WHERE o.status IN :statuses")
        List<Order> findByStatus(@Param("statuses") List<String> orderStatus);

        @Query("SELECT o FROM order o WHERE o.user IN :userIds")
        List<Order> findByUserId(@Param("userIds") List<String> userId);

        @Modifying
        @Transactional
        @Query("UPDATE order o SET o.status = :status WHERE o.id = :id")
        void updateOrder(@Param("id") Long id, @Param("status") String status);


}
