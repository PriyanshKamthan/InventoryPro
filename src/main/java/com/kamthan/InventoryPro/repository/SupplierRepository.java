package com.kamthan.InventoryPro.repository;

import com.kamthan.InventoryPro.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    List<Supplier> findByNameContainingIgnoreCase(String name);
    List<Supplier> findByEmailContainingIgnoreCase(String email);
    List<Supplier> findByGstNumberContainingIgnoreCase(String gstNumber);
    List<Supplier> findByPhoneContaining(String phone);
    Page<Supplier> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrGstNumberContainingIgnoreCaseOrPhoneContaining(
            String name, String email, String gstNumber, String phone, Pageable pageable
    );
    @Query(value = "SELECT * FROM supplier WHERE id = :id", nativeQuery = true)
    Optional<Supplier> findByIdIncludingDeleted(@Param("id") Long id);

}
