package com.kamthan.InventoryPro.service;

import com.kamthan.InventoryPro.dto.SupplierResponseDTO;
import com.kamthan.InventoryPro.model.Supplier;

import java.util.List;

public interface SupplierService {
    SupplierResponseDTO addSupplier(Supplier supplier);

    List<SupplierResponseDTO> getAllSuppliers();

    SupplierResponseDTO getSupplierById(Long id);

    public SupplierResponseDTO updateSupplier(Long id, Supplier updatedSupplier);

    void deleteSupplier(Long id);

    List<SupplierResponseDTO> searchSuppliers(String name, String phone, String email, String gstNumber);
}