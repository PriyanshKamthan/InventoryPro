package com.kamthan.InventoryPro.service;

import com.kamthan.InventoryPro.model.Supplier;

import java.util.List;

public interface SupplierService {
    Supplier addSupplier(Supplier supplier);
    List<Supplier> getAllSuppliers();
    Supplier getSupplierById(Long id);
    public Supplier updateSupplier(Long id, Supplier updatedSupplier);
    void deleteSupplier(Long id);
}
