package com.kamthan.InventoryPro.service;

import com.kamthan.InventoryPro.dto.StockMovementResponseDTO;
import com.kamthan.InventoryPro.model.Product;
import com.kamthan.InventoryPro.model.StockMovement;
import com.kamthan.InventoryPro.model.enums.MovementType;
import com.kamthan.InventoryPro.model.enums.ReferenceType;

import java.util.List;

public interface StockMovementService {
    StockMovement recordMovement(Product product,
                                 MovementType movementType,
                                 int quantity,
                                 int beforeQty,
                                 int afterQty,
                                 ReferenceType referenceType,
                                 Long referenceId);
    List<StockMovementResponseDTO> getMovementsForProduct(Long productId);
}