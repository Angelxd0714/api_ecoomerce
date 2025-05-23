package com.ecommerce.api.services;

import com.ecommerce.api.dto.HistorySoldRequest;
import com.ecommerce.api.dto.HistorySoldDTO;
import com.ecommerce.api.dto.response.ProductDTO; // Assuming ProductDTO is in this package
import com.ecommerce.api.dto.response.UsersDTO; // Assuming UsersDTO is in this package
import com.ecommerce.api.persistence.entities.HistorySold;
import com.ecommerce.api.persistence.entities.Product;
import com.ecommerce.api.persistence.entities.Users;
import com.ecommerce.api.persistence.interfaces.CrudHistorySold;
import com.ecommerce.api.persistence.repository.RepositoryHistorySold;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // For LocalDate/LocalDateTime
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistorySoldServices implements CrudHistorySold {

    private final RepositoryHistorySold repositoryHistorySold;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public HistorySoldDTO save(HistorySoldRequest request) {
        HistorySold historySold = HistorySold.builder()
                .sellerId(Users.builder().id(request.getSellerId()).build())
                .product(Product.builder().id(request.getProductId()).build())
                .quantitySold(request.getQuantitySold())
                .status(request.getStatus())
                .buyerId(Users.builder().id(request.getBuyerId()).build())
                .soldDate(LocalDate.now()) // Example, or pass from request if available
                // totalSold could be calculated based on product price and quantitySold
                .build();

        // Note: HistorySold entity has @PrePersist for createdAt, so no need to set it
        // here.
        // totalSold calculation would typically involve fetching product price.
        // For simplicity, it's omitted here but should be considered.

        HistorySold savedEntity = repositoryHistorySold.save(historySold);
        return convertToDTO(savedEntity);
    }

    @Override
    public List<HistorySoldDTO> findAll() {
        return repositoryHistorySold.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HistorySoldDTO findById(Long id) {
        HistorySold historySold = repositoryHistorySold.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("HistorySold not found with ID: " + id));
        return convertToDTO(historySold);
    }

    @Override
    public void deleteById(Long id) {
        if (!repositoryHistorySold.existsById(id)) {
            throw new IllegalArgumentException("HistorySold not found with ID: " + id + " for deletion.");
        }
        repositoryHistorySold.deleteById(id);
    }

    @Override
    public List<HistorySoldDTO> findBySellerId(Long sellerId) {
        Users seller = Users.builder().id(sellerId).build();
        return repositoryHistorySold.findBySellerId(seller).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<HistorySoldDTO> findByBuyerId(Long buyerId) {
        Users buyer = Users.builder().id(buyerId).build();
        return repositoryHistorySold.findByBuyerId(buyer).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private HistorySoldDTO convertToDTO(HistorySold entity) {
        HistorySoldDTO dto = HistorySoldDTO.builder()
                .id(entity.getId())
                .quantitySold(entity.getQuantitySold())
                .totalSold(entity.getTotalSold())
                .status(entity.getStatus())
                .soldDate(entity.getSoldDate())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();

        if (entity.getSellerId() != null) {
            dto.setSeller(objectMapper.convertValue(entity.getSellerId(), UsersDTO.class));
        }
        if (entity.getProduct() != null) {
            dto.setProduct(objectMapper.convertValue(entity.getProduct(), ProductDTO.class));
        }
        if (entity.getBuyerId() != null) {
            dto.setBuyer(objectMapper.convertValue(entity.getBuyerId(), UsersDTO.class));
        }
        return dto;
    }
}
