package com.kamthan.InventoryPro.repository;

import com.kamthan.InventoryPro.model.SaleItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
    @Query("""
        SELECT 
            p.id,
            p.name,
            SUM(si.quantity)
        FROM SaleItem si
        JOIN si.product p
        JOIN si.sale s
        WHERE s.saleDate BETWEEN :startDate AND :endDate
        GROUP BY p.id, p.name
        ORDER BY SUM(si.quantity) DESC
    """)
    List<Object[]> findTopSellingProducts(
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );
}
