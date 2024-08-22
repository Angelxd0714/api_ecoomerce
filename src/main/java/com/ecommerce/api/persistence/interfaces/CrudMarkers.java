package com.ecommerce.api.persistence.interfaces;

import com.ecommerce.api.persistence.entities.Markers;

public interface CrudMarkers {
    void save(Markers markers);
    Markers findById(String id);
    void update(Markers markers,String id);
    void delete(String id);
    Iterable<Markers> findAll();
}
