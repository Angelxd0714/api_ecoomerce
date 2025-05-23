package com.ecommerce.api.services;

import com.ecommerce.api.dto.HistoryBoughtRequest;
import com.ecommerce.api.dto.HistoryBoughtDTO;
import com.ecommerce.api.dto.response.ProductDTO;
import com.ecommerce.api.dto.response.UsersDTO;
import com.ecommerce.api.dto.response.PaymentsDTO; // Corrected package
import com.ecommerce.api.persistence.entities.HistoryBought;
import com.ecommerce.api.persistence.entities.Product;
import com.ecommerce.api.persistence.entities.Users;
import com.ecommerce.api.persistence.entities.Payments;
import com.ecommerce.api.persistence.interfaces.CrudHistoryBought;
import com.ecommerce.api.persistence.repository.RepositoryHistoryBought;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // Added for LocalDate

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryBoughtServices implements CrudHistoryBought {

    private final RepositoryHistoryBought repositoryHistoryBought;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public HistoryBoughtDTO save(HistoryBoughtRequest request) {
        HistoryBought historyBought = objectMapper.convertValue(request, HistoryBought.class);

        historyBought.setUser(Users.builder().id(request.getUserId()).build());
        historyBought.setProduct(Product.builder().id(request.getProductId()).build());
        if (request.getPaymentMethodId() != null) {
            historyBought.setPaymentMethod(Payments.builder().id(request.getPaymentMethodId()).build());
        }

        historyBought.setPurchaseDate(LocalDate.now());
        // historyBought.setTotal(request.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
        // // Example, ensure types match

        HistoryBought savedEntity = repositoryHistoryBought.save(historyBought);
        return convertToDTO(savedEntity);
    }

    @Override
    public List<HistoryBoughtDTO> findAll() {
        return repositoryHistoryBought.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HistoryBoughtDTO findById(Long id) {
        HistoryBought historyBought = repositoryHistoryBought.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("HistoryBought not found with ID: " + id));
        return convertToDTO(historyBought);
    }

    @Override
    public void deleteById(Long id) {
        if (!repositoryHistoryBought.existsById(id)) {
            throw new IllegalArgumentException("HistoryBought not found with ID: " + id + " for deletion.");
        }
        repositoryHistoryBought.deleteById(id);
    }

    @Override
    public List<HistoryBoughtDTO> findByUserId(Long userId) {
        Users user = Users.builder().id(userId).build();
        return repositoryHistoryBought.findByUserId(user).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private HistoryBoughtDTO convertToDTO(HistoryBought entity) {
        HistoryBoughtDTO dto = objectMapper.convertValue(entity, HistoryBoughtDTO.class);

        // Explicitly map nested DTOs
        if (entity.getUser() != null) {
            dto.setUser(objectMapper.convertValue(entity.getUser(), UsersDTO.class));
        }
        if (entity.getProduct() != null) {
            dto.setProduct(objectMapper.convertValue(entity.getProduct(), ProductDTO.class));
        }
        if (entity.getPaymentMethod() != null) {
            dto.setPaymentMethod(objectMapper.convertValue(entity.getPaymentMethod(), PaymentsDTO.class));
        }
        return dto;
    }
}
