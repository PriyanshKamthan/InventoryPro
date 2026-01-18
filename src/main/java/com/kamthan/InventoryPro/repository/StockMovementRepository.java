package com.kamthan.InventoryPro.repository;

import com.kamthan.InventoryPro.model.StockMovement;
import com.kamthan.InventoryPro.model.enums.ReferenceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findByProductIdOrderByTimestampDesc(Long productId);
    List<StockMovement> findByReferenceTypeAndReferenceId(ReferenceType referenceType, Long referenceId);
}
