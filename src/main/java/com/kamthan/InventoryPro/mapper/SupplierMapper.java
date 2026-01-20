package com.kamthan.InventoryPro.mapper;

import com.kamthan.InventoryPro.dto.SupplierResponseDTO;
import com.kamthan.InventoryPro.model.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {
    public SupplierResponseDTO toResponseDTO(Supplier supplier) {
        SupplierResponseDTO dto = new SupplierResponseDTO();
        dto.setId(supplier.getId());
        dto.setName(supplier.getName());
        dto.setEmail(supplier.getEmail());
        dto.setPhone(supplier.getPhone());
        dto.setAddress(supplier.getAddress());
        dto.setGstNumber(supplier.getGstNumber());
        dto.setCreatedAt(supplier.getCreatedAt());
        return dto;
    }
}
