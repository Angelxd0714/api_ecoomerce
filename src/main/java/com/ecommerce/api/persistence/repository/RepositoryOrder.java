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
public interface RepositoryOrder extends CrudRepository<Orders,Long> {

        @Query("SELECT o FROM Orders o WHERE o.orderDate = :orderDate")
        List<Orders> findByOrderDate(@Param("orderDate") LocalDate orderDate);

        @Query("SELECT o FROM Orders o WHERE o.status IN :statuses")
        List<Orders> findByStatus(@Param("statuses") String orderStatus);

        @Query("SELECT o FROM Orders o WHERE o.user.userId IN :userIds")
        List<Orders> findByUserId(@Param("userIds") Long userId);

        @Modifying
        @Transactional
        @Query("UPDATE Orders o SET o.status = :status WHERE o.id = :id")
        void updateOrder(@Param("id") Long id, @Param("status") String status);

        List<Orders> findAll();


}
