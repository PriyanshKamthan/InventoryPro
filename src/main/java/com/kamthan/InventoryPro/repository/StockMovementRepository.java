package com.kamthan.InventoryPro.repository;

import com.kamthan.InventoryPro.model.StockMovement;
import com.kamthan.InventoryPro.model.enums.ReferenceType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findByProductIdOrderByTimestampDesc(Long productId);
    List<StockMovement> findByReferenceTypeAndReferenceId(ReferenceType referenceType, Long referenceId);

    @Query("""
        SELECT 
            sm.timestamp,
            p.id,
            p.name,
            sm.movementType,
            sm.quantity,
            sm.beforeQty,
            sm.afterQty,
            sm.referenceType,
            sm.referenceId
        FROM StockMovement sm
        JOIN sm.product p
        ORDER BY sm.timestamp DESC
    """)
    List<Object[]> findRecentStockMovements(Pageable pageable);

}
