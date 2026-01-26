package com.kamthan.InventoryPro.repository;

import com.kamthan.InventoryPro.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategoryIgnoreCase(String category);

    List<Product> findByNameContainingIgnoreCaseAndCategoryIgnoreCase(String name, String category);

    @Query(value = "SELECT * FROM product WHERE id = :id", nativeQuery = true)
    Optional<Product> findByIdIncludingDeleted(@Param("id") Long id);

    @Query("""
       SELECT 
         COUNT(p.id),
         COALESCE(SUM(p.quantity), 0)
       FROM Product p
       WHERE p.isActive = true
    """)
    Object fetchStockSummary();
}
