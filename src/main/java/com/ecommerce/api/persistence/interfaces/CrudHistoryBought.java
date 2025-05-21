package com.ecommerce.api.persistence.interfaces;

import com.ecommerce.api.dto.HistoryBoughtRequest;
import com.ecommerce.api.dto.HistoryBoughtDTO;
import java.util.List;

public interface CrudHistoryBought {
    HistoryBoughtDTO save(HistoryBoughtRequest request);
    List<HistoryBoughtDTO> findAll();
    HistoryBoughtDTO findById(Long id);
    void deleteById(Long id);
    List<HistoryBoughtDTO> findByUserId(Long userId);
}
