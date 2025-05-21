package com.ecommerce.api.persistence.interfaces;

import com.ecommerce.api.dto.HistorySoldRequest;
import com.ecommerce.api.dto.HistorySoldDTO;
import java.util.List;

public interface CrudHistorySold {
    HistorySoldDTO save(HistorySoldRequest request);
    List<HistorySoldDTO> findAll();
    HistorySoldDTO findById(Long id);
    void deleteById(Long id);
    List<HistorySoldDTO> findBySellerId(Long sellerId);
    List<HistorySoldDTO> findByBuyerId(Long buyerId);
}
