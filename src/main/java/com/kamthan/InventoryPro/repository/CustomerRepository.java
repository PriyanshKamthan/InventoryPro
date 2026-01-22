package com.kamthan.InventoryPro.repository;

import com.kamthan.InventoryPro.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByNameContainingIgnoreCase(String name);
    List<Customer> findByEmailContainingIgnoreCase(String email);
    List<Customer> findByGstNumberContainingIgnoreCase(String gstNumber);
    List<Customer> findByPhoneContaining(String phone);
    @Query(value = "SELECT * FROM customer WHERE id = :id", nativeQuery = true)
    Optional<Customer> findByIdIncludingDeleted(@Param("id") Long id);

}
