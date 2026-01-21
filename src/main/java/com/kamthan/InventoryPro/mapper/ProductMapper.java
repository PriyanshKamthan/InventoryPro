package com.kamthan.InventoryPro.mapper;

import com.kamthan.InventoryPro.dto.ProductResponseDTO;
import com.kamthan.InventoryPro.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponseDTO toResponseDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCategory(product.getCategory());
        dto.setQuantity(product.getQuantity());
        dto.setPrice(product.getPrice());
        dto.setUnitOfMeasure(product.getUnitOfMeasure().name());
        return dto;
    }
}