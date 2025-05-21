package com.ecommerce.api.persistence.repository;

import com.ecommerce.api.persistence.entities.HistoryBought;
import com.ecommerce.api.persistence.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepositoryHistoryBought extends JpaRepository<HistoryBought, Long> {
    List<HistoryBought> findByUserId(Users user);
}
