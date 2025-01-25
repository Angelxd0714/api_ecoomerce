package com.ecommerce.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Markers;
import com.ecommerce.api.persistence.interfaces.CrudMarkers;
import com.ecommerce.api.persistence.repository.RepositoryMarkers;

@Service
public class MarkersServices implements CrudMarkers {
    @Autowired
    private RepositoryMarkers repositoryMarkers;

    @Override
    public void save(Markers markers) {
       repositoryMarkers.save(markers);
    }

    @Override
    public Markers findById(String id) {
       return repositoryMarkers.findById(id).orElse(null);
    }

    @Override
    public void update(Markers markers, Long id) {
        repositoryMarkers.findById(String.valueOf(id)).ifPresentOrElse(x->{
            repositoryMarkers.updateMarker(markers,id);
        },null);


    }

    @Override
    public void delete(String id) {
       repositoryMarkers.deleteById(id);
    }

    @Override
    public Iterable<Markers> findAll() {
       return repositoryMarkers.findAll();
    }

    public boolean existsById(String id) {
        return repositoryMarkers.existsById(id);
    }

}
