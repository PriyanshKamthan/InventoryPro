package com.kamthan.InventoryPro.repository;

import com.kamthan.InventoryPro.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findBySaleDateBetween(LocalDateTime from, LocalDateTime to);

    @Query("""
        SELECT 
            COUNT(s.id),
            COALESCE(SUM(s.totalAmount), 0)
        FROM Sale s
        WHERE s.saleDate BETWEEN :start AND :end
    """)
    Object fetchSaleSummary(LocalDateTime start, LocalDateTime end);

}
