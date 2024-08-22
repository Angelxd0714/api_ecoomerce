package com.ecommerce.api.persistence.interfaces;

import com.ecommerce.api.persistence.entities.Markers;

public interface CustomMarkersRepository {
    void updateMarker(String id, Markers updateRequest);
}
