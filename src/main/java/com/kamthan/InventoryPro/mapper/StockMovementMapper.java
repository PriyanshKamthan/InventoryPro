package com.kamthan.InventoryPro.mapper;

import com.kamthan.InventoryPro.dto.StockMovementResponseDTO;
import com.kamthan.InventoryPro.model.StockMovement;
import org.springframework.stereotype.Component;

@Component
public class StockMovementMapper {

    public StockMovementResponseDTO toResponseDTO(StockMovement sm) {

        StockMovementResponseDTO dto = new StockMovementResponseDTO();

        dto.setId(sm.getId());

        if (sm.getProduct() != null) {
            dto.setProductId(sm.getProduct().getId());
            dto.setProductName(sm.getProduct().getName());
        }

        dto.setMovementType(sm.getMovementType().name());
        dto.setQuantity(sm.getQuantity());
        dto.setBeforeQuantity(sm.getBeforeQty());
        dto.setAfterQuantity(sm.getAfterQty());

        if (sm.getReferenceType() != null) {
            dto.setReferenceType(sm.getReferenceType().name());
        }
        dto.setReferenceId(sm.getReferenceId());

        dto.setTimestamp(sm.getTimestamp());

        return dto;
    }
}