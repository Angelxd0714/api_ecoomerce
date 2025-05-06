package com.ecommerce.api.services;

import com.ecommerce.api.dto.request.MarkersRequest;
import com.ecommerce.api.dto.response.MarkersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.api.persistence.entities.Markers;
import com.ecommerce.api.persistence.interfaces.CrudMarkers;
import com.ecommerce.api.persistence.repository.RepositoryMarkers;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class MarkersServices implements CrudMarkers {
    @Autowired
    private RepositoryMarkers repositoryMarkers;

    @Transactional
    @Override
    public void save(MarkersRequest markers) {

        Markers markers1 = Markers.builder()
                .name(markers.getName())
                .description(markers.getDescription())
                .createdAt(markers.getCreatedAt())
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
                .createdAt(repositoryMarkers.findById(id).get().getCreatedAt())
                .updatedAt(repositoryMarkers.findById(id).get().getUpdatedAt())
                .build();

    }

    @Transactional
    @Override
    public void update(MarkersRequest markers, Long id) {
        try {
            Markers markers1 = Markers.builder()
                    .id(id)
                    .name(markers.getName())
                    .description(markers.getDescription())
                    .build();
            if (id == null) {
                throw new IllegalArgumentException("El id no puede ser nulo");
            }
            repositoryMarkers.updateMarker(markers1, id);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getCause());
        }

    }

    @Transactional
    @Override
    public void delete(Long id) {
        repositoryMarkers.deleteById(id);
    }

    @Override
    public List<MarkersDTO> findAll() {

        return repositoryMarkers.findAll().stream().map(x -> MarkersDTO.builder()
                .id(x.getId())
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
