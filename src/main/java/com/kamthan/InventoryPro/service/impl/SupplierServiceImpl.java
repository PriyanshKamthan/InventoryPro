package com.kamthan.InventoryPro.service.impl;

import com.kamthan.InventoryPro.exception.ResourceNotFoundException;
import com.kamthan.InventoryPro.model.Supplier;
import com.kamthan.InventoryPro.repository.SupplierRepository;
import com.kamthan.InventoryPro.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public Supplier addSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }
    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Supplier not found with id: "+id));
    }

    @Override
    public Supplier updateSupplier(Long id, Supplier updatedSupplier) {
        Supplier existing = getSupplierById(id);
        if(existing != null) {
            existing.setName(updatedSupplier.getName());
            existing.setAddress(updatedSupplier.getAddress());
            existing.setEmail(updatedSupplier.getEmail());
            existing.setGstNumber(updatedSupplier.getGstNumber());
            existing.setPhone(updatedSupplier.getPhone());
            return supplierRepository.save(existing);
        }
        return null;
    }

    @Override
    public void deleteSupplier(Long id) {
        getSupplierById(id);
        supplierRepository.deleteById(id);
    }
}
