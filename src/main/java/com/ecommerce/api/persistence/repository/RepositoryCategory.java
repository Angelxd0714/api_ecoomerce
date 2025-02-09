package com.ecommerce.api.persistence.repository;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.persistence.entities.Category;

import java.util.List;

@Repository
public interface RepositoryCategory extends CrudRepository<Category,Long> {

        @Modifying
        @Transactional
        @Query("UPDATE categories c SET c.name = :categoryName WHERE c.id = :categoryId")
        void updateCategory(@Param("categoryId") String categoryId, @Param("categoryName") String categoryName);


        List<Category> findCategoriesByName(String name);
}
