package com.ecommerce.api.services;

import com.ecommerce.api.dto.request.MarkersRequest;
import com.ecommerce.api.dto.response.MarkersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Markers;
import com.ecommerce.api.persistence.interfaces.CrudMarkers;
import com.ecommerce.api.persistence.repository.RepositoryMarkers;

import java.util.List;

@Service
public class MarkersServices implements CrudMarkers {
    @Autowired
    private RepositoryMarkers repositoryMarkers;

    @Override
    public void save(MarkersRequest markers) {

        Markers markers1 = Markers.builder()
                .name(markers.getName())
                .description(markers.getDescription())
                .createdAt(markers.getCreatedAt())
                .updatedAt(markers.getUpdatedAt())
                .updatedAt(markers.getUpdatedAt())
                .build();
        repositoryMarkers.save(markers1);
    }

    @Override
    public MarkersDTO findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
      if (!repositoryMarkers.existsById(id)) {
            throw new IllegalArgumentException("El marcador no existe");
        }
       if (repositoryMarkers.findById(id).isEmpty()) {
           throw new IllegalArgumentException("El marcador no existe");
       }

        return MarkersDTO.builder()
                .name(repositoryMarkers.findById(id).get().getName())
                .description(repositoryMarkers.findById(id).get().getDescription())
                .createdAt(repositoryMarkers.findById(id).get().getCreatedAt()).build();

    }

    @Override
    public void update(MarkersRequest markers, Long id) {
        Markers markers1 = Markers.builder()
                .name(markers.getName())
                .description(markers.getDescription())
                .createdAt(markers.getCreatedAt())
                .updatedAt(markers.getUpdatedAt())
                .build();
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        repositoryMarkers.findById(id).ifPresentOrElse(x->{
            repositoryMarkers.updateMarker(markers1,id);
        },null);


    }

    @Override
    public void delete(Long id) {
       repositoryMarkers.deleteById(id);
    }

    @Override
    public List<MarkersDTO> findAll() {


        return repositoryMarkers.findAll().stream().map(x-> MarkersDTO.builder()
                .name(x.getName())
                .description(x.getDescription())
                .createdAt(x.getCreatedAt())
                .updatedAt(x.getUpdatedAt())
                .build()).toList();
    }

    public boolean existsById(Long id) {
        return repositoryMarkers.existsById(id);
    }

}
