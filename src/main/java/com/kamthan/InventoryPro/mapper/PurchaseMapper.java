package com.kamthan.InventoryPro.mapper;

import com.kamthan.InventoryPro.dto.PurchaseItemResponseDTO;
import com.kamthan.InventoryPro.dto.PurchaseResponseDTO;
import com.kamthan.InventoryPro.model.Purchase;
import com.kamthan.InventoryPro.model.PurchaseItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PurchaseMapper {
    public PurchaseResponseDTO toResponseDTO(Purchase purchase) {
        PurchaseResponseDTO dto = new PurchaseResponseDTO();

        dto.setPurchaseId(purchase.getId());
        dto.setPurchaseDate(purchase.getPurchaseDate());
        dto.setTotalAmount(purchase.getTotalAmount());
        dto.setTotalTax(purchase.getTaxAmount());

        if (purchase.getSupplier() != null) {
            dto.setSupplierName(purchase.getSupplier().getName());
        }

        List<PurchaseItemResponseDTO> items = purchase.getItems().stream()
                .map(this::toItemDTO)
                .toList();

        dto.setItems(items);
        return dto;
    }

    private PurchaseItemResponseDTO toItemDTO(PurchaseItem item) {
        PurchaseItemResponseDTO itemDTO = new PurchaseItemResponseDTO();
        itemDTO.setProductId(item.getProduct().getId());
        itemDTO.setProductName(item.getProduct().getName());
        itemDTO.setQuantity(item.getQuantity());
        itemDTO.setPricePerUnit(item.getPricePerUnit());
        itemDTO.setTaxAmount(item.getTaxAmount());
        return itemDTO;
    }
}
