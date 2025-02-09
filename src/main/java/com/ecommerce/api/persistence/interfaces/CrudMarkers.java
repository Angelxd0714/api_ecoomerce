package com.ecommerce.api.persistence.interfaces;

import com.ecommerce.api.dto.request.MarkersRequest;
import com.ecommerce.api.persistence.entities.Markers;

import java.util.List;

public interface CrudMarkers {
    void save(MarkersRequest markers);
    MarkersRequest findById(Long id);
    void update(Markers markers,Long id);
    void delete(Long id);
    List<MarkersRequest> findAll();
}
