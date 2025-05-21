package com.ecommerce.api.persistence.repository;

import com.ecommerce.api.persistence.entities.HistorySold;
import com.ecommerce.api.persistence.entities.Users; // Make sure this import is correct
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepositoryHistorySold extends JpaRepository<HistorySold, Long> {
    List<HistorySold> findBySellerId(Users seller);
    List<HistorySold> findByBuyerId(Users buyer);
}
