package com.kamthan.InventoryPro.service.impl;

import com.kamthan.InventoryPro.dto.SupplierResponseDTO;
import com.kamthan.InventoryPro.exception.ResourceNotFoundException;
import com.kamthan.InventoryPro.mapper.SupplierMapper;
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
    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public SupplierResponseDTO addSupplier(Supplier supplier) {
        return supplierMapper.toResponseDTO(supplierRepository.save(supplier));
    }
    @Override
    public List<SupplierResponseDTO> getAllSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(supplierMapper::toResponseDTO)
                .toList();
    }

    @Override
    public SupplierResponseDTO getSupplierById(Long id) {
        return supplierMapper.toResponseDTO(supplierRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Supplier not found with id: "+id)));
    }

    @Override
    public SupplierResponseDTO updateSupplier(Long id, Supplier updatedSupplier) {
        Supplier existing = supplierRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Supplier not found with id: "+id));
        if(existing != null) {
            existing.setName(updatedSupplier.getName());
            existing.setAddress(updatedSupplier.getAddress());
            existing.setEmail(updatedSupplier.getEmail());
            existing.setGstNumber(updatedSupplier.getGstNumber());
            existing.setPhone(updatedSupplier.getPhone());
            return supplierMapper.toResponseDTO(supplierRepository.save(existing));
        }
        return null;
    }

    @Override
    public void deleteSupplier(Long id) {
        getSupplierById(id);
        supplierRepository.deleteById(id);
    }

    @Override
    public List<SupplierResponseDTO> searchSuppliers(String name, String phone, String email, String gstNumber) {
        if(name!=null && !name.isBlank()) {
            return supplierRepository.findByNameContainingIgnoreCase(name)
                    .stream()
                    .map(supplierMapper::toResponseDTO)
                    .toList();
        }
        if(phone!=null && !phone.isBlank()) {
            return supplierRepository.findByPhoneContaining(phone)
                    .stream()
                    .map(supplierMapper::toResponseDTO)
                    .toList();
        }
        if(email!=null && !email.isBlank()) {
            return supplierRepository.findByEmailContainingIgnoreCase(email)
                    .stream()
                    .map(supplierMapper::toResponseDTO)
                    .toList();
        }
        if(gstNumber!=null && !gstNumber.isBlank()) {
            return supplierRepository.findByGstNumberContainingIgnoreCase(gstNumber)
                    .stream()
                    .map(supplierMapper::toResponseDTO)
                    .toList();
        }
        return getAllSuppliers();
    }
}
