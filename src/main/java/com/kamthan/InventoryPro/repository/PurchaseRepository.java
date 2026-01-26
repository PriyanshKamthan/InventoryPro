package com.kamthan.InventoryPro.repository;

import com.kamthan.InventoryPro.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByPurchaseDateBetween(LocalDateTime from,LocalDateTime to);

    @Query("""
        SELECT 
            COUNT(p.id),
            COALESCE(SUM(p.totalAmount), 0)
        FROM Purchase p
        WHERE p.purchaseDate BETWEEN :start AND :end
    """)
    Object fetchPurchaseSummary(LocalDateTime start,LocalDateTime end);

}
