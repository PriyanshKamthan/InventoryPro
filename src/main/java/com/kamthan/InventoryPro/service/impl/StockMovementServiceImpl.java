package com.kamthan.InventoryPro.service.impl;

import com.kamthan.InventoryPro.dto.StockMovementResponseDTO;
import com.kamthan.InventoryPro.exception.InvalidRequestException;
import com.kamthan.InventoryPro.exception.ResourceNotFoundException;
import com.kamthan.InventoryPro.mapper.StockMovementMapper;
import com.kamthan.InventoryPro.model.Product;
import com.kamthan.InventoryPro.model.StockMovement;
import com.kamthan.InventoryPro.model.enums.MovementType;
import com.kamthan.InventoryPro.model.enums.ReferenceType;
import com.kamthan.InventoryPro.repository.StockMovementRepository;
import com.kamthan.InventoryPro.service.ProductService;
import com.kamthan.InventoryPro.service.StockMovementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class StockMovementServiceImpl implements StockMovementService {
    @Autowired
    private StockMovementRepository stockMovementRepository;
    @Autowired
    private StockMovementMapper stockMovementMapper;
    @Override
    public StockMovement recordMovement(Product product, MovementType movementType, int quantity, int beforeQty, int afterQty, ReferenceType referenceType, Long referenceId) {

        log.info(
                "Stock movement initiated: productId={}, movementType={}, qty={}, before={}, after={}, refType={}, refId={}",
                product.getId(), movementType, quantity, beforeQty, afterQty, referenceType, referenceId);

        if (quantity <= 0) {
            log.error("Invalid stock movement quantity: productId={}, qty={}", product.getId(), quantity);
            throw new InvalidRequestException("Stock movement quantity must be greater than zero");
        }

        StockMovement stockMovement = new StockMovement();
        stockMovement.setProduct(product);
        stockMovement.setMovementType((movementType));
        stockMovement.setQuantity(quantity);
        stockMovement.setBeforeQty(beforeQty);
        stockMovement.setAfterQty(afterQty);
        stockMovement.setReferenceType(referenceType);
        stockMovement.setReferenceId(referenceId);
        stockMovement.setTimestamp(LocalDateTime.now());

        StockMovement saved = stockMovementRepository.save(stockMovement);
        log.info("Stock movement recorded successfully | stockMovementId={} | productId={}",
                saved.getId(), product.getId());

        return saved;
    }
    @Override
    public List<StockMovementResponseDTO> getMovementsForProduct(Long productId) {
        log.info("Fetching StockMovement for ProductId: {}",productId);

        List<StockMovement> stockMovementList = stockMovementRepository.findByProductIdOrderByTimestampDesc(productId);
        if(stockMovementList.isEmpty()) {
            log.info("No Stock Movement available for product, id:{}",productId);
            throw new ResourceNotFoundException("No Stock Movement available for product, id:"+productId);
        }

        return stockMovementList.stream()
                .map(stockMovementMapper::toResponseDTO)
                .toList();
    }
}
