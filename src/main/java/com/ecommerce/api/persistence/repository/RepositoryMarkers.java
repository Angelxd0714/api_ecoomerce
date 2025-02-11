package com.ecommerce.api.persistence.repository;

import java.util.*;

import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.persistence.entities.Markers;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface RepositoryMarkers extends CrudRepository<Markers,Long> {
    Optional<Markers> findByName(String name);
    @Modifying
    @Query("UPDATE markers m SET m.name = :#{#markers.name}, m.description = :#{#markers.description} WHERE m.id = :id")
    void updateMarker(@Param("markers") Markers markers, @Param("id") Long id);
    List<Markers> findAll();
}
