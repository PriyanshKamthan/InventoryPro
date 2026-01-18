package com.kamthan.InventoryPro.service.impl;

import com.kamthan.InventoryPro.model.Product;
import com.kamthan.InventoryPro.model.StockMovement;
import com.kamthan.InventoryPro.model.enums.MovementType;
import com.kamthan.InventoryPro.model.enums.ReferenceType;
import com.kamthan.InventoryPro.repository.StockMovementRepository;
import com.kamthan.InventoryPro.service.StockMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockMovementServiceImpl implements StockMovementService {
    @Autowired
    private StockMovementRepository stockMovementRepository;
    @Override
    public StockMovement recordMovement(Product product, MovementType movementType, int quantity, int beforeQty, int afterQty, ReferenceType referenceType, Long referenceId) {
        StockMovement stockMovement = new StockMovement();
        stockMovement.setProduct(product);
        stockMovement.setMovementType((movementType));
        stockMovement.setQuantity(quantity);
        stockMovement.setBeforeQty(beforeQty);
        stockMovement.setAfterQty(afterQty);
        stockMovement.setReferenceType(referenceType);
        stockMovement.setReferenceId(referenceId);
        stockMovement.setTimestamp(LocalDateTime.now());
        return stockMovementRepository.save(stockMovement);
    }

    @Override
    public void updateReferenceId(List<Long> stockMovementIdList, Long referenceId) {
        for(Long stockMovementId : stockMovementIdList) {
            StockMovement sm = stockMovementRepository.findById(stockMovementId).orElse(null);
            if(sm != null) {
                sm.setReferenceId(referenceId);
                stockMovementRepository.save(sm);
            }
        }
    }
    @Override
    public List<StockMovement> getMovementsForProduct(Long productId) {
        return stockMovementRepository.findByProductIdOrderByTimestampDesc(productId);
    }
}
